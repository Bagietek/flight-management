package com.example.FlightManagment.view;

import com.example.FlightManagment.controller.LoadEntityController;
import com.example.FlightManagment.dto.ApiResult;
import com.example.FlightManagment.dto.FlightLoadDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class FlightNumberAndDate {
    LoadEntityController loadEntityController;

    public FlightNumberAndDate(LoadEntityController loadEntityController) {
        this.loadEntityController = loadEntityController;
    }

    public void flightNumberAndDate(){
        int flightNumber;
        System.out.println("Enter flight number: ");
        Scanner scanner = new Scanner(System.in);
        while (true){
            try{
                flightNumber = Integer.parseInt(scanner.nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid input, not an integer");
            }
        }
        System.out.println("Enter flight date (yyyy-mm-dd): ");
        LocalDate flightDate;
        while (true){
            try{
                flightDate = LocalDate.parse(scanner.nextLine());
                break;
            }catch (DateTimeParseException e){
                System.out.println("Invalid input date");
            }
        }
        ApiResult<FlightLoadDto> apiResult = loadEntityController.getFlightLoad(flightNumber, flightDate);
        if (apiResult.isError()){
            System.out.println(apiResult.getErrors());
            return;
        }
        System.out.println("Baggage weights " + apiResult.getResult().getBaggageWeight() + " kg");
        System.out.println("Cargo weights " + apiResult.getResult().getCargoWeight() + " kg");
        System.out.println("Total weights " + apiResult.getResult().getTotalWeight() + " kg");
    }
}
