package io.codelex.flightplanner.database;

import io.codelex.flightplanner.classes.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDatabaseRepository extends JpaRepository <Airport, String> {
    @Query("select a from Airport a where lower(a.airport) like concat('%', :search, '%')"
    + "or lower(a.country) like concat('%', :search, '%')"
    + "or lower(a.city) like concat('%', :search, '%')")
    List<Airport> findByCityAndCountryAndAirportContaining(String search);
}
