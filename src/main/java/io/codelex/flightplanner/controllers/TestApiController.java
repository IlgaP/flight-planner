package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.service.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/testing-api")
public class TestApiController {

    private FlightService flightService;

    public TestApiController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PostMapping("/clear")
    public void clear() {
        flightService.clear();
    }

}

