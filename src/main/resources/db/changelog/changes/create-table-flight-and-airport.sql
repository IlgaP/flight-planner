--liquibase formatted sql

        --changeset ilga:1

        CREATE TABLE airport
        (
            airport VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,
            country VARCHAR(255) NOT NULL,
            city VARCHAR(255) NOT NULL
        );

        CREATE TABLE flight
        (
        id BIGINT PRIMARY KEY NOT NULL UNIQUE,
        airport_from VARCHAR(255) NOT NULL,
        airport_to VARCHAR(255) NOT NULL,
        carrier VARCHAR(255) NOT NULL,
        departure_time TIMESTAMP NOT NULL,
        arrival_time TIMESTAMP NOT NULL,
        CONSTRAINT flight_airport_from_fkey FOREIGN KEY (airport_from) REFERENCES airport (airport),
        CONSTRAINT flight_airport_to_fkey FOREIGN KEY (airport_to) REFERENCES airport (airport),
        CONSTRAINT flight_unique UNIQUE (airport_from, airport_to, carrier, departure_time, arrival_time)
        );

        CREATE SEQUENCE flight_sequence START WITH 1 INCREMENT BY 1;

