package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.Flight;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlightEntityRepoImpTest {

    @Test
    public void readingJsonTest() throws IOException, ParseException {
        //given
        Configuration conf = mock(Configuration.class);
        LinkedList<Flight> allFlights;
        FlightEntityRepoImp flightEntityRepoImp = new FlightEntityRepoImp();
        //when
        when(conf.getFlightEntitiesFileName())
                .thenReturn("/test/readingJsonTestFlight.json");
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
        Flight first = allFlights.getFirst();
        assertTrue(first.getDepartureDate() instanceof LocalDate);
        assertEquals("2020-02-23",first.getDepartureDate().toString());
        assertEquals("PPX",first.getArrivalAirportIATACode());
        assertEquals("LAX",first.getDepartureAirportIATACode());
        assertEquals(4629,(int) first.getFlightNumber());
    }

}