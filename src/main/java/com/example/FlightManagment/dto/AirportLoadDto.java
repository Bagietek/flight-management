package com.example.FlightManagment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AirportLoadDto {
    private Integer numberOfFlightsDeparting;
    private Integer numberOfFlightsArriving;
    private Integer numberOfBaggagePiecesDeparting;
    private Integer numberOfBaggagePiecesArriving;

}

