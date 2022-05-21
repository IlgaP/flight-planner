package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.classes.AddFlightRequest;
import io.codelex.flightplanner.classes.Flight;

public abstract class AbstractRepository {

    protected boolean isValidDate(Flight flight) {
        return flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getArrivalTime().equals(flight.getDepartureTime());
    }

    protected boolean containsSameAirport(AddFlightRequest addFlightRequest) {
        return addFlightRequest.getFrom().getAirport().trim().equalsIgnoreCase(addFlightRequest.getTo().getAirport().trim());
    }

    protected boolean flightContainsSearchPhrase(String search, Flight flight) {
        return flight.getFrom().getCountry().toLowerCase().contains(search.trim().toLowerCase())
                || flight.getFrom().getCity().toLowerCase().contains(search.trim().toLowerCase())
                || flight.getFrom().getAirport().toLowerCase().contains(search.trim().toLowerCase());
    }

}
