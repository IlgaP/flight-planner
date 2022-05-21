package io.codelex.flightplanner.service;

import io.codelex.flightplanner.classes.*;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        return flightRepository.addFlight(addFlightRequest);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteFlight(id);
    }

    public Flight getFlight(Long id) {
        return flightRepository.getFlight(id);
    }

    public void clear() {
        flightRepository.clear();

    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq) {
        return flightRepository.searchFlight(searchFlightReq);
    }

    public Flight findFlight(Long id) {
        return flightRepository.getFlight(id);
    }
}
