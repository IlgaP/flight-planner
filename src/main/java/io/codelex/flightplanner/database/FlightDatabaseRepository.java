package io.codelex.flightplanner.database;

import io.codelex.flightplanner.classes.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {
}
