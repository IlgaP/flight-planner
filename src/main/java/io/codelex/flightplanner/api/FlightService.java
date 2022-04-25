package io.codelex.flightplanner.api;

import io.codelex.flightplanner.classes.AddFlightRequest;
import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.classes.Flight;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(@RequestBody AddFlightRequest addFlightRequest) {
        return flightRepository.addFlight(addFlightRequest);
    }

    public  void deleteFlight(@PathVariable("id") int id) {
        flightRepository.deleteFlight(id);
    }

    public Flight getFlight(@PathVariable("id") int id) {
        return flightRepository.getFlight(id);
    }

    public void clear() {
        flightRepository.clear();

    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    }
