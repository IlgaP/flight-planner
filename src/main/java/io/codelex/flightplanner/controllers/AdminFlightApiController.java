package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.classes.AddFlightRequest;
import io.codelex.flightplanner.classes.Flight;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightApiController {

    private FlightService flightService;

    public AdminFlightApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return flightService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Long id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id) {
        return flightService.getFlight(id);
    }
}
