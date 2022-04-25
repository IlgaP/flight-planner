package io.codelex.flightplanner.api;

import io.codelex.flightplanner.classes.AddFlightRequest;
import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.classes.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public synchronized Flight addFlight(@RequestBody AddFlightRequest addFlightRequest) {
        for (Flight eachFlight : flights) {
            if ((eachFlight.getFrom().toString()).equals(addFlightRequest.getFrom().toString())
                    && (eachFlight.getTo().toString()).equals(addFlightRequest.getTo().toString())
                    && (eachFlight.getCarrier()).equals(addFlightRequest.getCarrier())
                    && eachFlight.getDepartureTime().format(formatter).equals(addFlightRequest.getDepartureTime())
                    && eachFlight.getArrivalTime().format(formatter).equals(addFlightRequest.getArrivalTime()))
            {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }

            if (addFlightRequest.getFrom().getCountry().trim().equalsIgnoreCase(addFlightRequest.getTo().getCountry().trim())
                    && addFlightRequest.getFrom().getCity().trim().equalsIgnoreCase(addFlightRequest.getTo().getCity().trim())
                    && addFlightRequest.getFrom().getAirport().trim().equalsIgnoreCase(addFlightRequest.getTo().getAirport().trim())) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

        Flight flight = new Flight(count, addFlightRequest.getFrom(),
                addFlightRequest.getTo(), addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter));

            if (flight.getArrivalTime().isBefore(flight.getDepartureTime())
                    || flight.getArrivalTime().equals(flight.getDepartureTime())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

        flights.add(flight);
        count++;
        return flight;
    }

    public synchronized void deleteFlight(@PathVariable("id") int id) {
        flights.removeIf(eachFlight -> eachFlight.getId() == id);
    }

    public synchronized Flight getFlight(@PathVariable("id") int id) {
        for (Flight eachFlight : flights) {
            if (eachFlight.getId() == id) {
                return eachFlight;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void clear() {
        flights.clear();
    }

    public synchronized List<Airport> searchAirports(String search) {
        for (Flight flight : flights) {
            if (flight.getFrom().getCountry().toLowerCase().contains(search.trim().toLowerCase())
            || flight.getFrom().getCity().toLowerCase().contains(search.trim().toLowerCase())
            || flight.getFrom().getAirport().toLowerCase().contains(search.trim().toLowerCase())) {
                if (!(airports.contains(flight.getFrom()))) {
                    airports.add(flight.getFrom());
                }
            }
        }
        return airports;
    }






}
