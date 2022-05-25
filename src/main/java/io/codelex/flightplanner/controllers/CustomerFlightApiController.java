package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.classes.Flight;
import io.codelex.flightplanner.classes.PageResult;
import io.codelex.flightplanner.classes.SearchFlightRequest;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public PageResult searchFlight(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return flightService.searchFlight(searchFlightRequest.toDomain());
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlight(@PathVariable("id") Long id) {
        return flightService.getFlight(id);
    }

}
