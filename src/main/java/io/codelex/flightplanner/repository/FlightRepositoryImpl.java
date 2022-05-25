package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.classes.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class FlightRepositoryImpl extends AbstractRepository implements FlightRepository {

    private final List<Flight> flights = new ArrayList<>();
    private final List<Airport> airports = new ArrayList<>();
    private static Long count = 1L;
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

    public synchronized void deleteFlight(Long id) {
        flights.removeIf(eachFlight -> Objects.equals(eachFlight.getId(), id));
    }

    public synchronized Flight getFlight(Long id) {
        return flights.stream()
                .filter(flight -> Objects.equals(flight.getId(), id))
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

    public PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq) {
        if (searchFlightReq.getTo().equals(searchFlightReq.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Flight> flightList = flights.stream().filter(searchFlightReq::equalsFlight).toList();
        return new PageResult<>(flightList);
    }
}
