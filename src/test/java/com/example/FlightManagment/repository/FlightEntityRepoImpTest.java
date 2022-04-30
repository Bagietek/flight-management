package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.Flight;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class FlightEntityRepoImpTest {

    @Test
    public void readingJsonTest() throws IOException, ParseException {
        //given
        LinkedList<Flight> allFlights;
        FlightEntityRepoImp flightEntityRepoImp = new FlightEntityRepoImp();
        //when
        allFlights = flightEntityRepoImp.getAllFlights();
        //then
        Integer flightIdCheck = 0;
        for (Flight f : allFlights){
            assertEquals(flightIdCheck,f.getFlightId());
            flightIdCheck++;
            assertNotNull(f.getFlightNumber());
            assertNotNull(f.getDepartureAirportIATACode());
            assertNotNull(f.getArrivalAirportIATACode());
            assertNotNull(f.getDepartureDate());
        }
    }

}