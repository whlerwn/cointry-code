package org.example.service;

import org.example.config.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CountryNumberServiceTest {

    @Mock
    private Parser parser;

    @InjectMocks
    private CountryNumberService countryNumberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCountry() {
        Map<String, List<String>> catalog = new HashMap<>();
        catalog.put("1242", List.of("Bahamas"));
        catalog.put("1", Arrays.asList("UnitedStates", "Canada"));
        catalog.put("7", List.of("Russia"));
        catalog.put("77", List.of("Kazakhstan"));

        when(parser.getMap()).thenReturn(catalog);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("number", "77112227231");

        Object result = countryNumberService.findCountry(inputMap);

        Map<String, Object> expected = Map.of("country", List.of("Kazakhstan"));
        assertEquals(expected, result);
    }

    @Test
    public void testFindCountryWithNoMatch() {
        Map<String, List<String>> catalog = new HashMap<>();
        catalog.put("1242", List.of("Bahamas"));
        catalog.put("1", Arrays.asList("UnitedStates", "Canada"));
        catalog.put("7", List.of("Russia"));
        catalog.put("77", List.of("Kazakhstan"));

        when(parser.getMap()).thenReturn(catalog);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("number", "999999999999999");

        Object result = countryNumberService.findCountry(inputMap);

        Map<String, String> expected = Map.of("message", "No match.");
        assertEquals(expected, result);
    }

    @Test
    public void testFindCountryWithWrongSympols() {
        Map<String, List<String>> catalog = new HashMap<>();
        catalog.put("1242", List.of("Bahamas"));
        catalog.put("1", Arrays.asList("UnitedStates", "Canada"));
        catalog.put("7", List.of("Russia"));
        catalog.put("77", List.of("Kazakhstan"));

        when(parser.getMap()).thenReturn(catalog);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("number", "+11165384765");

        Object result = countryNumberService.findCountry(inputMap);

        Map<String, String> expected =
                Map.of("message", "Invalid data type. Please enter numbers without symbols.");
        assertEquals(expected, result);
    }
}