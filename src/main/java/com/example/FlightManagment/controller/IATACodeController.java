package com.example.FlightManagment.controller;

import com.example.FlightManagment.dto.AirportLoadDto;
import com.example.FlightManagment.dto.ApiResult;
import com.example.FlightManagment.model.Baggage;
import com.example.FlightManagment.model.Flight;
import com.example.FlightManagment.model.LoadEntity;
import com.example.FlightManagment.repository.CargoEntityRepo;
import com.example.FlightManagment.repository.FlightEntityRepo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class IATACodeController {
    private static final LocalDate _oldestFlightDate = LocalDate.of(2014,1,1);

    FlightEntityRepo flightEntityRepo;
    CargoEntityRepo loadEntityRepo;

    public IATACodeController(FlightEntityRepo flightEntityRepository, CargoEntityRepo loadEntityRepository) {
        this.flightEntityRepo = flightEntityRepository;
        this.loadEntityRepo = loadEntityRepository;
    }

    public ApiResult<AirportLoadDto> getAirportLoad(String iataCode, LocalDate flightDate){
        ApiResult<AirportLoadDto> result = new ApiResult<>();
        Pattern iataCheck = Pattern.compile("[A-Z]{3}");
        if(!iataCheck.matcher(iataCode).matches()){
            result.addError("Invalid IATA airport code");
        }
        if(_oldestFlightDate.compareTo(flightDate) > 0 || LocalDate.now().compareTo(flightDate) < 0){
            result.addError("Invalid date");
        }
        if(result.isError()){
            return result;
        }

        LinkedList<Flight> flightList = flightEntityRepo.getByAirportAndDate(iataCode, flightDate);
        if (flightList.isEmpty()){
            result.addError("No flights found that day");
            return result;
        }
        Integer numberOfFlightsDeparting = 0;
        Integer numberOfFlightsArriving = 0;
        Integer numberOfBaggagePiecesDeparting = 0;
        Integer numberOfBaggagePiecesArriving = 0;
        for (Flight flight : flightList){
            if(flight.getArrivalAirportIATACode().equals(iataCode)){
                numberOfFlightsArriving++;
            }
            if(flight.getDepartureAirportIATACode().equals(iataCode)){
                numberOfFlightsDeparting++;
            }
            LoadEntity loadEntity = loadEntityRepo.getAllForFlightId(flight.getFlightId());
            for (Baggage baggage : loadEntity.getBaggages()){
                if(flight.getDepartureAirportIATACode().equals(iataCode)){
                    numberOfBaggagePiecesDeparting += baggage.getPieces();
                }
                if(flight.getArrivalAirportIATACode().equals(iataCode)){
                    numberOfBaggagePiecesArriving += baggage.getPieces();
                }
            }
        }
        result.setResult(
                AirportLoadDto.builder()
                        .numberOfBaggagePiecesDeparting(numberOfBaggagePiecesDeparting)
                        .numberOfBaggagePiecesArriving(numberOfBaggagePiecesArriving)
                        .numberOfFlightsArriving(numberOfFlightsArriving)
                        .numberOfFlightsDeparting(numberOfFlightsDeparting)
                        .build());
        return result;
    }

}
