package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.*;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.junit.Assert.*;

public class CargoEntityRepoImpTest {

    @Test
    public void readingJsonTest() throws IOException, ParseException {
        //given
        Configuration conf = mock(Configuration.class);
        LoadEntity load;
        Integer flightId = 0;
        CargoEntityRepoImp cargoEntityRepoImp = new CargoEntityRepoImp();
        //when
        when(conf.getCargoEntitiesFileName())
                .thenReturn("/test/readingJsonTestCargo.json");
        load = cargoEntityRepoImp.getAllForFlightId(flightId);
        //then
        assertNotNull(load);
        Integer idCheck = 0;
        Integer loadPieces = 0;
        Integer loadWeight = 0;
        for (Cargo c : load.getCargos()){
            assertEquals(idCheck,c.getId());
            idCheck++;
            assertNotNull(c.getPieces());
            assertNotNull(c.getWeight());
            assertNotNull(c.getWeight().getWeightUnit());
            assertTrue(c.getWeight().getWeightUnit().equals(WeightUnit.LB) || c.getWeight().getWeightUnit().equals(WeightUnit.KG));
            loadPieces += c.getPieces();
            loadWeight += c.getWeightValue();
        }
        assertEquals(2013,(int) loadPieces);
        assertEquals(1723,(int) loadWeight);
        idCheck = 0;
        loadPieces = 0;
        loadWeight = 0;
        for (Baggage b : load.getBaggages()){
            assertEquals(idCheck,b.getId());
            idCheck++;
            assertNotNull(b.getPieces());
            assertNotNull(b.getWeight());
            assertNotNull(b.getWeight().getWeightUnit());
            assertTrue(b.getWeight().getWeightUnit().equals(WeightUnit.LB) || b.getWeight().getWeightUnit().equals(WeightUnit.KG));
            loadPieces += b.getPieces();
            loadWeight += b.getWeightValue();
        }
        assertEquals(2288,(int) loadWeight);
        assertEquals(2903,(int) loadPieces);
    }

}