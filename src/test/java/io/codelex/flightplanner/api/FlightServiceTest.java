package io.codelex.flightplanner.api;

import io.codelex.flightplanner.classes.AddFlightRequest;
import io.codelex.flightplanner.classes.Airport;
import io.codelex.flightplanner.classes.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    FlightService flightService;

    @Test
    public void testAddFlight() {

        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallinn", "EST");
        String departureTime = "2022-05-02 09:00";
        String arrivalTime = "2022-05-02 10:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        AddFlightRequest addFlightRequest = new AddFlightRequest(from, to, "AirBaltic", departureTime, arrivalTime);

        Mockito.doAnswer(invocation -> {
            AddFlightRequest request = invocation.getArgument(0);
            Assertions.assertEquals(addFlightRequest, request);
            return new Flight(3,
                    request.getFrom(),
                    request.getTo(),
                    request.getCarrier(),
                    LocalDateTime.parse(request.getDepartureTime(), formatter),
                    LocalDateTime.parse(request.getArrivalTime(), formatter));
        }).when(flightRepository).addFlight(addFlightRequest);

        Flight flight = flightService.addFlight(addFlightRequest);

        Mockito.verify(flightRepository).addFlight(addFlightRequest);
    }
}
