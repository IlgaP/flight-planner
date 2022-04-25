package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.api.FlightService;
import org.springframework.web.bind.annotation.*;


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


//        @GetMapping("/flights")
//        public List<Flight> getFlights(){
//            return flights;
//        }
    }

