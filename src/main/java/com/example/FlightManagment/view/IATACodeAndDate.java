package com.example.FlightManagment.view;

import com.example.FlightManagment.controller.IATACodeController;
import com.example.FlightManagment.dto.AirportLoadDto;
import com.example.FlightManagment.dto.ApiResult;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class IATACodeAndDate {
    IATACodeController iataCodeController;

    public IATACodeAndDate(IATACodeController iataCodeController) {
        this.iataCodeController = iataCodeController;
    }

    public void iataCodeAndDate(){
        System.out.println("Enter IATA airport code: ");
        Scanner scanner = new Scanner(System.in);
        String iataCode = scanner.nextLine();
        System.out.println("Enter flight date (yyyy-mm-dd): ");
        LocalDate flightDate;
        while (true){
            try{
                String dateString = scanner.nextLine();
                flightDate = LocalDate.parse(dateString);
                break;
            }catch (DateTimeParseException e){
                System.out.println("Invalid input date");
            }
        }
        ApiResult<AirportLoadDto> apiResult = iataCodeController.getAirportLoad(iataCode, flightDate);
        if (apiResult.isError()){
            System.out.println(apiResult.getErrors());
            return;
        }
        System.out.println("Number of flight departing from this airport: " + apiResult.getResult().getNumberOfFlightsDeparting());
        System.out.println("Number of flight arriving to this airport: " + apiResult.getResult().getNumberOfFlightsArriving());
        System.out.println("Total number of baggage arriving to this airport: " + apiResult.getResult().getNumberOfBaggagePiecesArriving());
        System.out.println("Total number of baggage departing from this airport: " + apiResult.getResult().getNumberOfBaggagePiecesDeparting());
    }
}
