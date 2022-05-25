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
@Transactional
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
    public Flight addFlight(AddFlightRequest addFlightRequest) {

        if (containsSameAirport(addFlightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(
//                addFlightRequest.getFrom(),
//                addFlightRequest.getTo(),
                addFlightRequest.getCarrier(),
                LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter));

        flight.setFrom(getOrCreate(addFlightRequest.getFrom()));
        flight.setTo(getOrCreate(addFlightRequest.getTo()));

        if (flightDatabaseRepository.existsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
                flight.getFrom(),
                flight.getTo(),
                flight.getCarrier(),
                flight.getDepartureTime(),
                flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (isValidDate(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightDatabaseRepository.save(flight);
    }

    private Airport getOrCreate(Airport airport) {
        Optional <Airport> maybeAirport = airportDatabaseRepository.findById(airport.getAirport());
        return maybeAirport.orElseGet(() -> airportDatabaseRepository.save(airport));
    }

    @Override
    public void deleteFlight(Long id) {
        if (flightDatabaseRepository.existsById(id)) {
            flightDatabaseRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.OK);
        }
    }

    @Override
    public Flight getFlight(Long id) {
        if (flightDatabaseRepository.existsById(id)) {
            return flightDatabaseRepository.getById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void clear() {
        flightDatabaseRepository.deleteAll();
        airportDatabaseRepository.deleteAll();
    }

    @Override
    public List<Airport> searchAirports(String search) {
        search = search.trim().toLowerCase();
       return airportDatabaseRepository.findByCityAndCountryAndAirportContaining(search);
    }

    @Override
    public PageResult<Flight> searchFlight(SearchFlightReq searchFlightReq) {
        if (searchFlightReq.getTo().equals(searchFlightReq.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime departureDateStart = searchFlightReq.getDepartureDate().atStartOfDay();
        LocalDateTime departureDateEnd = departureDateStart.plusDays(1L);

        List<Flight> flightList = flightDatabaseRepository.searchFlight(
                searchFlightReq.getFrom(),
                searchFlightReq.getTo(),
                departureDateStart,
                departureDateEnd);
        return new PageResult<>(flightList);
    }
}
