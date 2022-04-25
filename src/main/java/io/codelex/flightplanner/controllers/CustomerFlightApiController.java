package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.api.FlightService;
import io.codelex.flightplanner.classes.Flight;
import io.codelex.flightplanner.classes.PageResult;
import io.codelex.flightplanner.classes.SearchFlightRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")

public class CustomerFlightApiController {

    private FlightService flightService;

    public CustomerFlightApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@PathParam("search") String search) {
       return flightService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlight(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {


    }

}
