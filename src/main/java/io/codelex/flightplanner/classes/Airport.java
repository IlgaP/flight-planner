package io.codelex.flightplanner.classes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Airport {
    @NotNull
    @NotBlank
    private String country;
    @NotNull
    @NotBlank
    private String city;
    @NotNull
    @NotBlank
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "{"
                + "country='" + country + '\''
                + ", city='" + city + '\''
                + ", airport='" + airport + '\'' + '}';
    }
}
