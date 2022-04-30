package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.*;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CargoEntityRepoImpTest {

    @Test
    public void readingJsonTest() throws IOException, ParseException {
        //given
        LoadEntity load;
        Integer flightId = 1;
        CargoEntityRepoImp cargoEntityRepoImp = new CargoEntityRepoImp();
        //when
        load = cargoEntityRepoImp.getAllForFlightId(flightId);
        //then
        assertNotNull(load);
        Integer cargoIdCheck = 0;
        for (Cargo c : load.getCargos()){
            assertEquals(cargoIdCheck,c.getId());
            cargoIdCheck++;
            assertNotNull(c.getPieces());
            assertNotNull(c.getWeight());
            assertNotNull(c.getWeight().getWeightUnit());
            assertFalse(c.getWeight().getWeight() == 0);
            assertFalse(c.getPieces() == 0);
        }
        Integer baggageIdCheck = 0;
        for (Baggage b : load.getBaggages()){
            assertEquals(baggageIdCheck,b.getId());
            baggageIdCheck++;
            assertNotNull(b.getPieces());
            assertNotNull(b.getWeight());
            assertNotNull(b.getWeight().getWeightUnit());
            assertTrue(b.getWeight().getWeightUnit().equals(WeightUnit.LB) || b.getWeight().getWeightUnit().equals(WeightUnit.KG));
            assertFalse(b.getWeight().getWeight() == 0);
            assertFalse(b.getPieces() == 0);
        }
    }

}