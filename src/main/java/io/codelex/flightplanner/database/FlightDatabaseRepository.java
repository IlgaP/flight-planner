package io.codelex.flightplanner.database;

import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.classes.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {
    boolean existsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
            Airport from,
            Airport to,
            String carrier,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime
    );

    @Query("select f from Flight f where f.from.airport = :from and f.to.airport = :to "
            + "and f.departureTime >= :departureDateStart and f.departureTime < :departureDateEnd")
    List<Flight> searchFlight(
            @Param("from") String from,
            @Param("to") String to,
            @Param("departureDateStart") LocalDateTime departureDateStart,
            @Param("departureDateEnd") LocalDateTime departureDateEnd
    );
}
