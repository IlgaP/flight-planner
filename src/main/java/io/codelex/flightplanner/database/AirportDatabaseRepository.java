package io.codelex.flightplanner.database;

import io.codelex.flightplanner.classes.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportDatabaseRepository extends JpaRepository <Airport, String> {
}
