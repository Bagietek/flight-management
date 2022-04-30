package com.example.FlightManagment.view;

import com.example.FlightManagment.controller.IATACodeController;
import com.example.FlightManagment.controller.LoadEntityController;
import com.example.FlightManagment.repository.CargoEntityRepo;
import com.example.FlightManagment.repository.CargoEntityRepoImp;
import com.example.FlightManagment.repository.FlightEntityRepo;
import com.example.FlightManagment.repository.FlightEntityRepoImp;

import java.util.Scanner;

public class Menu {
    public void showMenu(){
        FlightEntityRepo flightEntityRepo = null;
        CargoEntityRepo cargoEntityRepo = null;
        try{
            flightEntityRepo = new FlightEntityRepoImp();
            cargoEntityRepo = new CargoEntityRepoImp();
        }catch (Exception e){
            System.out.println("Error loading files");
            e.printStackTrace();
            return;
        }
        IATACodeController iataCodeController = new IATACodeController(flightEntityRepo, cargoEntityRepo);
        LoadEntityController loadEntityController = new LoadEntityController(flightEntityRepo, cargoEntityRepo);
        FlightNumberAndDate flightNumberAndDate = new FlightNumberAndDate(loadEntityController);
        IATACodeAndDate iataCodeAndDate = new IATACodeAndDate(iataCodeController);
        quit:
        while (true){
            System.out.println("1. Flight number and date");
            System.out.println("2. IATA Airport code and date");
            System.out.println("3. Quit");
            System.out.print("\nSelect the type of request: ");
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();
            switch (input){
                case "1":
                    flightNumberAndDate.flightNumberAndDate();
                    break;
                case "2":
                    iataCodeAndDate.iataCodeAndDate();
                    break;
                case "3":
                    System.out.println("Exiting the program...");
                    break quit;
                default:
                    System.out.println("Request not supported");
                    break;
            }
        }

    }
}
