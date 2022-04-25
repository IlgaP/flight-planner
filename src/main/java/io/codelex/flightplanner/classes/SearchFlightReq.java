package io.codelex.flightplanner.classes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SearchFlightReq {

    private String from;
    private String to;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    public SearchFlightReq(String from, String to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public boolean equalsFlight(Flight flight) {
        return flight.getFrom().getAirport().equals(from)
                && flight.getTo().getAirport().equals(to)
                && flight.getDepartureTime().toLocalDate().isEqual(departureDate);
    }
}
