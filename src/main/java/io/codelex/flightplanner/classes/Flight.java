package io.codelex.flightplanner.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static io.codelex.flightplanner.repository.FlightRepositoryImpl.formatter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Flight {
    @Id
    @SequenceGenerator(
            name = "flight_sequence",
            sequenceName = "flight_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "flight_sequence"
    )
    private Long id;
    @JoinColumn(name = "airport_from")
    @ManyToOne
    @NotNull
    private Airport from;
    @JoinColumn(name = "airport_to")
    @ManyToOne
    @NotNull
    private Airport to;
    private String carrier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    public Flight() {
    }
    public Flight(Long id, Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "Flight{"
                + "id=" + id
                + ", from=" + from
                + ", to=" + to
                + ", carrier='" + carrier + '\''
                + ", departureTime=" + departureTime
                + ", arrivalTime=" + arrivalTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return getId() == flight.getId()
                && Objects.equals(getFrom(), flight.getFrom())
                && Objects.equals(getTo(), flight.getTo())
                && Objects.equals(getCarrier(), flight.getCarrier())
                && Objects.equals(getDepartureTime(), flight.getDepartureTime())
                && Objects.equals(getArrivalTime(), flight.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFrom(), getTo(), getCarrier(), getDepartureTime(), getArrivalTime());
    }

    public boolean isSameFlight(AddFlightRequest addFlightRequest) {
        return (this.getFrom().toString()).equals(addFlightRequest.getFrom().toString())
                && (this.getTo().toString()).equals(addFlightRequest.getTo().toString())
                && (this.getCarrier()).equals(addFlightRequest.getCarrier())
                && this.getDepartureTime().format(formatter).equals(addFlightRequest.getDepartureTime())
                && this.getArrivalTime().format(formatter).equals(addFlightRequest.getArrivalTime());
    }
}
