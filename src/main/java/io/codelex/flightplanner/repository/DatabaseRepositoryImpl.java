package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.classes.*;
import io.codelex.flightplanner.database.AirportDatabaseRepository;
import io.codelex.flightplanner.database.FlightDatabaseRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Repository
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class DatabaseRepositoryImpl extends AbstractRepository implements FlightRepository {

    private final FlightDatabaseRepository flightDatabaseRepository;
    private final AirportDatabaseRepository airportDatabaseRepository;

    public DatabaseRepositoryImpl(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    @Transactional
    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Optional<Airport> maybeAirportTo = airportDatabaseRepository.findById(addFlightRequest.getTo().getAirport());
        if (maybeAirportTo.isEmpty()) {
            Airport airport = addFlightRequest.getTo();
            airportDatabaseRepository.save(airport);
        }

        Optional<Airport> maybeAirportFrom = airportDatabaseRepository.findById(addFlightRequest.getFrom().getAirport());
        if (maybeAirportFrom.isEmpty()) {
            airportDatabaseRepository.save(addFlightRequest.getFrom());
        }

        if (flightDatabaseRepository.findAll().stream().anyMatch(flight -> flight.isSameFlight(addFlightRequest))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (containsSameAirport(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(
                addFlightRequest.getFrom(),
                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter));


        if (isValidDate(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightDatabaseRepository.save(flight);
    }



    @Override
    @Transactional
    public void deleteFlight(Long id) {
        if (flightDatabaseRepository.existsById(id)) {
            flightDatabaseRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.OK);
        }
    }

    @Override
    @Transactional
    public Flight getFlight(Long id) {
        if (flightDatabaseRepository.existsById(id)) {
            return flightDatabaseRepository.getById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void clear() {
        flightDatabaseRepository.deleteAll();
    }

    @Override
    @Transactional
    public List<Airport> searchAirports(String search) {
       return airportDatabaseRepository.findAll().stream().filter(airport -> airportContainsSearchPhrase(search, airport)).toList();
    }

    private static boolean airportContainsSearchPhrase(String search, Airport airport) {
        return airport.getCountry().toLowerCase().contains(search.trim().toLowerCase())
                || airport.getCity().toLowerCase().contains(search.trim().toLowerCase())
                || airport.getAirport().toLowerCase().contains(search.trim().toLowerCase());
    };

    @Override
    @Transactional
    public PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq) {
        return null;
    }
}
