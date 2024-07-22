package org.example.config;

import org.example.model.Code;
import org.example.model.Country;
import org.example.repository.CodeRepository;
import org.example.repository.CountryRepository;
import org.springframework.stereotype.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Component
public class Parser {

    private static final String URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
    private static final Integer TIMEOUT = 3000;
    private static final String PATH = "src/main/resources/cleanup.sql";

    private final CodeRepository codeRepository;
    private final CountryRepository countryRepository;
    private final DataSource dataSource;

    private final Map<String, List<String>> map = new HashMap<>();

    public Parser(
            CodeRepository codeRepository,
            CountryRepository countryRepository,
            DataSource dataSource) {
        this.codeRepository = codeRepository;
        this.countryRepository = countryRepository;
        this.dataSource = dataSource;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        loadMap();
        saveMap();
    }

    @PreDestroy
    public void cleanup() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            String script = new String(Files.readAllBytes(Paths.get(PATH)));
            stmt.execute(script);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMap() {
        map.keySet().forEach(number -> {
            List<String> servings = map.get(number);
            servings.forEach(serv -> saving(number, serv));
        });
    }

    private void saving(String number, String serving) {
        Country country = new Country(serving);
        Code code = new Code(country, number);

        countryRepository.saveAndFlush(country);
        codeRepository.saveAndFlush(code);
    }

    private void loadMap() {
        Elements countries = getElements(getPage());

        countries.stream().skip(2).forEach(element -> {
            List<Node> childNodes = element.childNodes();

            List<Node> nodes = childNodes.get(1).childNodes();
            String serving = nodes.size() == 1 ? nodes.get(0).toString() : nodes.get(1).toString();

            String code = childNodes.get(3).childNodes().get(1).childNodes().get(0).toString();
            parseLine(serving, code);
        });
    }

    private void parseLine(String serving, String code) {
        if (code.contains("(")) {
            int index = code.indexOf("(");
            String numberString = code.substring(0, index).trim();
            String[] numbers = code.substring(code.indexOf('(') + 1, code.indexOf(')')).split(",");

            Arrays.stream(numbers)
                    .map(String::trim)
                    .map(number -> numberString + number)
                    .forEach(number -> map.computeIfAbsent(number, k -> new ArrayList<>()).add(serving));
        } else {
            map.computeIfAbsent(code, k -> new ArrayList<>()).add(serving);
        }
    }

    private Document getPage() {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(URL), TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    private Elements getElements(Document page) {
        Element table = page.select("table[class=wikitable sortable sticky-header-multi]").first();
        return table.select("tr");
    }
}
