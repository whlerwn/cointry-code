package org.example.service;

import org.example.config.Parser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryNumberService {

    private final static String NUMBER = "number";
    private final static String WARN_MESSAGE = "Invalid data type. Please enter numbers without symbols.";
    private final static String EMPTY_MESSAGE = "No match.";

    private final Parser parser;

    public CountryNumberService(
            Parser parser
    ) {
        this.parser = parser;
    }

    public Object findCountry(Map<String, String> map) {
        String num = map.get(NUMBER);

        boolean isNumeric = num.matches("\\d+");

        Map<String, Object> result = new HashMap<>();
        if (!isNumeric) {
            result.put("message", WARN_MESSAGE);
        }

        Map<String, List<String>> catalog = parser.getMap();

        catalog.keySet().stream().sorted((e1, e2) -> Integer.compare(e2.length(), e1.length()))
                .filter(num::startsWith)
                .findFirst()
                .ifPresent(key -> result.put("country", catalog.get(key)));

        if (result.isEmpty()) {
            result.put("message", EMPTY_MESSAGE);
        }
        return result;
    }
}
