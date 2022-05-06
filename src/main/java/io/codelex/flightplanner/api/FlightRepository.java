package io.codelex.flightplanner.api;

import io.codelex.flightplanner.classes.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Repository
public class FlightRepository {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Airport> airports = new ArrayList<>();
    private static int count = 1;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {

        if (flights.stream().anyMatch(flight -> flight.isSameFlight(addFlightRequest))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (containsSameAirport(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(count,
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter));

        if (isValidDate(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        flights.add(flight);
        count++;
        return flight;
    }

    private boolean isValidDate(Flight flight) {
        return flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getArrivalTime().equals(flight.getDepartureTime());
    }

    private boolean containsSameAirport(AddFlightRequest addFlightRequest) {
        return addFlightRequest.getFrom().getAirport().trim().equalsIgnoreCase(addFlightRequest.getTo().getAirport().trim());
    }

    public synchronized void deleteFlight(int id) {
        flights.removeIf(eachFlight -> eachFlight.getId() == id);
    }

    public synchronized Flight getFlight(int id) {
        return flights.stream()
                .filter(flight -> flight.getId() == (id))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void clear() {
        flights.clear();
    }

    public synchronized List<Airport> searchAirports(String search) {

        for (Flight flight : flights) {
            if (flightContainsSearchPhrase(search, flight)) {
                if (!(airports.contains(flight.getFrom()))) {
                    airports.add(flight.getFrom());
                }
            }
        }
        return airports;
    }

    private boolean flightContainsSearchPhrase(String search, Flight flight) {
        return flight.getFrom().getCountry().toLowerCase().contains(search.trim().toLowerCase())
                || flight.getFrom().getCity().toLowerCase().contains(search.trim().toLowerCase())
                || flight.getFrom().getAirport().toLowerCase().contains(search.trim().toLowerCase());
    }


    public PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq) {
        if (searchFlightReq.getTo().equals(searchFlightReq.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Flight> flightList = flights.stream().filter(searchFlightReq::equalsFlight).toList();
        return new PageResult<>(flightList);
    }
}
