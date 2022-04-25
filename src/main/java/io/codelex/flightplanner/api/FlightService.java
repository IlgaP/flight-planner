package io.codelex.flightplanner.api;

import io.codelex.flightplanner.classes.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        return flightRepository.addFlight(addFlightRequest);
    }

    public void deleteFlight(int id) {
        flightRepository.deleteFlight(id);
    }

    public Flight getFlight(int id) {
        return flightRepository.getFlight(id);
    }

    public void clear() {
        flightRepository.clear();

    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public PageResult searchFlight(SearchFlightReq searchFlightReq) {
        return flightRepository.searchFlight(searchFlightReq);
    }

    public Flight findFlight(int id) {
        return flightRepository.getFlight(id);
    }
}
