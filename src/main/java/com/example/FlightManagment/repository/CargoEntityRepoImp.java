package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class CargoEntityRepoImp implements CargoEntityRepo{
    HashMap<Integer, LoadEntity> allLoadList;

    public CargoEntityRepoImp() throws IOException, ParseException {
        File configFile = new File("./src/main/resources/Config.json");
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(configFile);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        File file = new File("./src/main/resources/" + jsonObject.get("CargoEntity"));
        fileReader.close();
        fileReader = new FileReader(file);
        JSONArray loadList = (JSONArray) jsonParser.parse(fileReader);
        allLoadList = new HashMap<>();
        for (Object o : loadList){
            jsonObject = (JSONObject) o;
            Integer flightId = Math.toIntExact((Long) jsonObject.get("flightId"));
            JSONArray baggageArray = (JSONArray) jsonObject.get("baggage");
            LinkedList<Baggage> baggageLinkedList = new LinkedList<>();
            for (Object obj : baggageArray){
                JSONObject baggageObject = (JSONObject) obj;
                Baggage baggage = new Baggage();
                baggage.setId(Math.toIntExact((Long) baggageObject.get("id")));
                baggage.setWeight(new Weight(Math.toIntExact((Long) baggageObject.get("weight")), WeightUnit.valueOf(String.valueOf(baggageObject.get("weightUnit")).toUpperCase())));
                baggage.setPieces(Math.toIntExact((Long) baggageObject.get("pieces")));
                baggageLinkedList.add(baggage);
            }
            JSONArray cargoList = (JSONArray) jsonObject.get("cargo");
            LinkedList<Cargo> cargoLinkedList = new LinkedList<>();
            for (Object obj : cargoList){
                JSONObject cargoObject = (JSONObject) obj;
                Cargo cargo = new Cargo();
                cargo.setId(Math.toIntExact((Long) cargoObject.get("id")));
                cargo.setWeight(new Weight(Math.toIntExact((Long) cargoObject.get("weight")), WeightUnit.valueOf(String.valueOf(cargoObject.get("weightUnit")).toUpperCase())));
                cargo.setPieces(Math.toIntExact((Long) cargoObject.get("pieces")));
                cargoLinkedList.add(cargo);
            }
            allLoadList.put(flightId, new LoadEntity(flightId, baggageLinkedList, cargoLinkedList));
        }
    }

    public LoadEntity getAllForFlightId(Integer flightId) {
        if (allLoadList.containsKey(flightId)) {
            return allLoadList.get(flightId);
        }
        return null;
    }
}
