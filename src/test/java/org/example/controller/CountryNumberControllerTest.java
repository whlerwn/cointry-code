package org.example.controller;

import org.example.service.CountryNumberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CountryNumberControllerTest {

    @Mock
    private CountryNumberService service;

    @InjectMocks
    private CountryNumberController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetCountryByNumber_Bahamas() throws Exception {
        String phoneNumber = "12423222931";
        List<String> expectedCountries = List.of("Bahamas");

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(expectedCountries);

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCountries)));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }



    @Test
    void testGetCountryByNumber_UnitedStatesAndCanada() throws Exception {
        String phoneNumber = "11165384765";
        List<String> expectedCountries = Arrays.asList("United States", "Canada");

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(expectedCountries);

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCountries)));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }

    @Test
    void testGetCountryByNumber_Russia() throws Exception {
        String phoneNumber = "71423423412";
        List<String> expectedCountries = List.of("Russia");

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(expectedCountries);

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCountries)));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }

    @Test
    void testGetCountryByNumber_Kazakhstan() throws Exception {
        String phoneNumber = "77112227231";
        List<String> expectedCountries = List.of("Kazakhstan");

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(expectedCountries);

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCountries)));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }

    @Test
    void testGetCountryByNumber_NoMatch() throws Exception {
        String phoneNumber = "999999999999999";
        String expectedResponse = "No match.";

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(List.of(expectedResponse));

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expectedResponse))));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }

    @Test
    void testGetCountryByNumber_InvalidSymbols() throws Exception {
        String phoneNumber = "+11165384765";
        String expectedResponse = "Invalid data type. Please enter numbers without symbols.";

        when(service.findCountry(Map.of("number", phoneNumber))).thenReturn(List.of(expectedResponse));

        mockMvc.perform(post("/api/country/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"" + phoneNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(expectedResponse))));

        verify(service, times(1)).findCountry(Map.of("number", phoneNumber));
    }

}