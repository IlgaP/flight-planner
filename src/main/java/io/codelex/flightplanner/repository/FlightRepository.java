package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.classes.*;

import java.util.List;

public interface FlightRepository {
    Flight addFlight(AddFlightRequest addFlightRequest);

    void deleteFlight(Long id);

    Flight getFlight(Long id);

    void clear();

    List<Airport> searchAirports(String search);

    PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq);
}
