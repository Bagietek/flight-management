package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.Flight;

import java.time.LocalDate;
import java.util.LinkedList;

public interface FlightEntityRepo {
    Flight getFlightByNumberAndDate(Integer flightNumber, LocalDate flightDate);
    LinkedList<Flight> getByAirportAndDate(String iataCode, LocalDate flightDate);
}

