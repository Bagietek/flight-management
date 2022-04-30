package com.example.FlightManagment.controller;

import com.example.FlightManagment.dto.ApiResult;
import com.example.FlightManagment.dto.FlightLoadDto;
import com.example.FlightManagment.model.*;
import com.example.FlightManagment.repository.CargoEntityRepo;
import com.example.FlightManagment.repository.CargoEntityRepoImp;
import com.example.FlightManagment.repository.FlightEntityRepo;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadEntityControllerTest {
    FlightEntityRepo flightEntityRepo;
    CargoEntityRepo cargoEntityRepo;

    private LoadEntityController getMockedController(){
        flightEntityRepo = mock(FlightEntityRepo.class);
        cargoEntityRepo = mock(CargoEntityRepo.class);
        return new LoadEntityController(flightEntityRepo, cargoEntityRepo);
    }

    @Test
    public void invalidInputNumberTest() {
        //given
        LoadEntityController controller = getMockedController();
        Integer flightNumber = 40000;
        LocalDate flightDate = LocalDate.of(2018, 4,5);
        //when
        ApiResult<FlightLoadDto> result = controller.getFlightLoad(flightNumber, flightDate);
        //then
        assertEquals(true,result.isError());
        assertEquals(1,result.getErrors().size());
        assertEquals("Invalid flight number",result.getErrors().get(0));
    }

    @Test
    public void invalidInputDateTest(){
        //given
        LoadEntityController controller = getMockedController();
        Integer flightNumber = 5000;
        LocalDate flightDate = LocalDate.of(1999, 4,5);
        //when
        ApiResult<FlightLoadDto> result = controller.getFlightLoad(flightNumber, flightDate);
        //then
        assertEquals(true,result.isError());
        assertEquals(1,result.getErrors().size());
        assertEquals("Invalid date",result.getErrors().get(0));
    }

    @Test
    public void weightCalculationTest(){
        //given
        LoadEntityController controller = getMockedController();
        Integer flightId = 5;
        Integer flightNumber = 5000;
        LocalDate flightDate = LocalDate.of(2017, 4,5);
        LinkedList<Cargo> cargo = new LinkedList<>();
        cargo.add(new Cargo(1,5, new Weight(5, WeightUnit.KG)));
        cargo.add(new Cargo(1,10, new Weight(3, WeightUnit.KG)));
        cargo.add(new Cargo(1,15, new Weight(10, WeightUnit.LB)));
        cargo.add(new Cargo(1,20, new Weight(20, WeightUnit.LB)));
        cargo.add(new Cargo(1,5, new Weight(1, WeightUnit.LB)));
        LinkedList<Baggage> baggage = new LinkedList<>();
        baggage.add(new Baggage(1,5,new Weight(5,WeightUnit.KG)));
        baggage.add(new Baggage(1,10,new Weight(3,WeightUnit.KG)));
        baggage.add(new Baggage(1,15,new Weight(20,WeightUnit.LB)));
        baggage.add(new Baggage(1,5,new Weight(5,WeightUnit.LB)));
        //when
        when(flightEntityRepo.getFlightByNumberAndDate(flightNumber,flightDate))
                .thenReturn(new Flight(flightId,flightNumber,"","",flightDate));
        when(cargoEntityRepo.getAllForFlightId(flightId))
                .thenReturn(new LoadEntity(flightId, baggage, cargo));
        ApiResult<FlightLoadDto> result = controller.getFlightLoad(flightNumber,flightDate);
        //then
        assertEquals(false,result.isError());
        assertEquals(22, (int) (result.getResult().getCargoWeight()));
        assertEquals(19,(int) result.getResult().getBaggageWeight());
        assertEquals(41, (int) result.getResult().getTotalWeight());
    }
}