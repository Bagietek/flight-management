package com.example.FlightManagment.controller;

import com.example.FlightManagment.dto.AirportLoadDto;
import com.example.FlightManagment.dto.ApiResult;
import com.example.FlightManagment.dto.FlightLoadDto;
import com.example.FlightManagment.model.*;
import com.example.FlightManagment.repository.CargoEntityRepo;
import com.example.FlightManagment.repository.FlightEntityRepo;
import org.junit.Test;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class IATACodeControllerTest {
    FlightEntityRepo flightEntityRepo;
    CargoEntityRepo cargoEntityRepo;

    private IATACodeController getMockedController(){
        flightEntityRepo = mock(FlightEntityRepo.class);
        cargoEntityRepo = mock(CargoEntityRepo.class);
        return new IATACodeController(flightEntityRepo, cargoEntityRepo);
    }

    @Test
    public void invalidInputDateTest(){
        //given
        IATACodeController controller = getMockedController();
        String flightCode = "KRK";
        LocalDate flightDate = LocalDate.of(1999, 4,5);
        //when
        ApiResult<AirportLoadDto> result = controller.getAirportLoad(flightCode, flightDate);
        //then
        assertEquals(true,result.isError());
        assertEquals(1,result.getErrors().size());
        assertEquals("Invalid date",result.getErrors().get(0));
    }

    @Test
    public void invalidIataCodeTest(){
        //given
        IATACodeController controller = getMockedController();
        String flightCode = "kr4";
        LocalDate flightDate = LocalDate.of(2019, 4,5);
        //when
        ApiResult<AirportLoadDto> result = controller.getAirportLoad(flightCode, flightDate);
        //then
        assertEquals(true,result.isError());
        assertEquals(1,result.getErrors().size());
        assertEquals("Invalid IATA airport code",result.getErrors().get(0));
    }

    @Test
    public void noTrafficTest(){
        //given
        IATACodeController controller = getMockedController();
        String flightCode = "KRK";
        LocalDate flightDate = LocalDate.of(2019, 4,5);
        //when
        ApiResult<AirportLoadDto> result = controller.getAirportLoad(flightCode, flightDate);
        //then
        assertEquals(true,result.isError());
        assertEquals(1,result.getErrors().size());
        assertEquals("No flights found that day",result.getErrors().get(0));
    }

    @Test
    public void calculateBaggagePiecesAndFlightsTest(){
        //given
        IATACodeController controller = getMockedController();
        String flightCodeDep = "ANC";
        String flightCodeArr = "SEA";
        LocalDate flightDate = LocalDate.of(2019, 4,5);
        Integer flightId = 1;
        Integer flightNumber = 5000;
        LinkedList<Cargo> cargo = new LinkedList<>();
        cargo.add(new Cargo(1,5, new Weight(5, WeightUnit.KG)));
        cargo.add(new Cargo(2,10, new Weight(3, WeightUnit.KG)));
        cargo.add(new Cargo(3,15, new Weight(10, WeightUnit.LB)));
        cargo.add(new Cargo(4,20, new Weight(20, WeightUnit.LB)));
        cargo.add(new Cargo(5,5, new Weight(1, WeightUnit.LB)));
        LinkedList<Baggage> baggage = new LinkedList<>();
        baggage.add(new Baggage(1,5,new Weight(5,WeightUnit.KG)));
        baggage.add(new Baggage(2,10,new Weight(3,WeightUnit.KG)));
        baggage.add(new Baggage(3,15,new Weight(20,WeightUnit.LB)));
        baggage.add(new Baggage(4,5,new Weight(5,WeightUnit.LB)));
        LinkedList<Flight> flights = new LinkedList<>();
        flights.add(new Flight(flightId,flightNumber,flightCodeDep,flightCodeArr,flightDate));
        //when
        when(flightEntityRepo.getByAirportAndDate(flightCodeDep,flightDate))
                .thenReturn(flights);
        when(cargoEntityRepo.getAllForFlightId(flightId))
                .thenReturn(new LoadEntity(flightId, baggage, cargo));
        ApiResult<AirportLoadDto> result = controller.getAirportLoad(flightCodeDep,flightDate);
        //then
        assertEquals(false,result.isError());
        assertEquals(0, (int) result.getResult().getNumberOfFlightsArriving());
        assertEquals(1, (int) result.getResult().getNumberOfFlightsDeparting());
        assertEquals(35, (int) result.getResult().getNumberOfBaggagePiecesDeparting());
        assertEquals(0, (int) result.getResult().getNumberOfBaggagePiecesArriving());
    }
}