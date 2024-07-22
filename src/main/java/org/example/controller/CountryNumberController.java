package org.example.controller;

import org.example.service.CountryNumberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/country")
public class CountryNumberController {

    private final CountryNumberService service;

    public CountryNumberController(CountryNumberService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getCountryByNumber(@RequestBody Map<String, String> map) {
        return service.findCountry(map);
    }
}
