package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.Flight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class FlightEntityRepoImp implements FlightEntityRepo{
    private LinkedList<Flight> allFlights;

    public FlightEntityRepoImp() throws IOException, ParseException {
        File configFile = new File("./src/main/resources/Config.json");
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(configFile);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        File file = new File("./src/main/resources/" + jsonObject.get("FlightEntity"));
        fileReader.close();
        fileReader = new FileReader(file);
        JSONArray flightList = (JSONArray) jsonParser.parse(fileReader);
        allFlights = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        for (Object o : flightList){
            Flight flight = new Flight();
            jsonObject = (JSONObject) o;
            flight.setFlightId(Math.toIntExact((Long) jsonObject.get("flightId")));
            flight.setFlightNumber(Math.toIntExact((Long) jsonObject.get("flightNumber")));
            flight.setDepartureAirportIATACode((String) jsonObject.get("departureAirportIATACode"));
            flight.setArrivalAirportIATACode((String) jsonObject.get("arrivalAirportIATACode"));
            LocalDate date = LocalDate.parse((CharSequence) jsonObject.get("departureDate"), formatter);

            flight.setDepartureDate(date);
            allFlights.add(flight);
        }
        fileReader.close();
    }

    public LinkedList<Flight> getAllFlights() {
        return allFlights;
    }

    public Flight getFlightByNumberAndDate(Integer flightNumber, LocalDate flightDate){
        for (Flight flight : allFlights){
            if (flight.getFlightNumber().equals(flightNumber)){
                if (flight.getDepartureDate().equals(flightDate)){
                    return flight;
                }
            }
        }
        return null;
    }

    @Override
    public LinkedList<Flight> getByAirportAndDate(String iataCode, LocalDate flightDate) {
        LinkedList<Flight> foundFlights = new LinkedList<>();
        for (Flight flight : allFlights) {
            if(flight.getArrivalAirportIATACode().equals(iataCode) || flight.getDepartureAirportIATACode().equals(iataCode)){
                if(flight.getDepartureDate().equals(flightDate)){
                    foundFlights.add(flight);
                }
            }
        }
        return foundFlights;
    }
}
