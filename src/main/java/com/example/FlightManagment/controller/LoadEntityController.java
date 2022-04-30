package com.example.FlightManagment.controller;

import com.example.FlightManagment.dto.ApiResult;
import com.example.FlightManagment.dto.FlightLoadDto;
import com.example.FlightManagment.model.*;
import com.example.FlightManagment.repository.CargoEntityRepo;
import com.example.FlightManagment.repository.FlightEntityRepo;

import java.time.LocalDate;

public class LoadEntityController {
    private static final LocalDate _oldestFlightDate = LocalDate.of(2014, 1, 1);

    FlightEntityRepo flightEntityRepo;
    CargoEntityRepo loadEntityRepo;

    public LoadEntityController(FlightEntityRepo flightEntityRepo, CargoEntityRepo loadEntityRepo) {
        this.flightEntityRepo = flightEntityRepo;
        this.loadEntityRepo = loadEntityRepo;
    }

    public ApiResult<FlightLoadDto> getFlightLoad(Integer flightNumber, LocalDate flightDate){
        ApiResult<FlightLoadDto> result = new ApiResult<>();
        if(flightNumber < 1000 || flightNumber > 9999) {
            result.addError("Invalid flight number");
        }
        if(_oldestFlightDate.compareTo(flightDate) > 0 || LocalDate.now().compareTo(flightDate) < 0){
            result.addError("Invalid date");
        }
        if (result.isError()){
            return  result;
        }

        Flight flight = flightEntityRepo.getFlightByNumberAndDate(flightNumber, flightDate);
        if(flight == null){
            result.addError("No plane found");
            return result;
        }

        LoadEntity load = loadEntityRepo.getAllForFlightId(flight.getFlightId());
        if(load == null){
            result.addError("Plane has no load");
            return result;
        }
        float baggageWeight = 0;
        float cargoWeight = 0;

        for (Baggage baggage : load.getBaggages()){
            if(baggage.getWeightUnit() == WeightUnit.LB){
                float z = baggage.getWeightValue() * baggage.getWeight().ConversionFactor();
                baggageWeight += z;
            }else{
                baggageWeight += baggage.getWeightValue();
            }
        }
        for (Cargo cargo : load.getCargos()){
            if(cargo.getWeightUnit() == WeightUnit.LB){
                float z = cargo.getWeightValue() * cargo.getWeight().ConversionFactor();
                cargoWeight += z;
            }else{
                cargoWeight += cargo.getWeightValue();
            }
        }
        result.setResult(FlightLoadDto.builder()
                .cargoWeight((int) cargoWeight)
                .baggageWeight((int) baggageWeight)
                .totalWeight((int) (baggageWeight + cargoWeight))
                .build());
        return result;
    }
}
