package com.example.FlightManagment.repository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {
    public String getCargoEntitiesFileName() throws IOException, ParseException {
        File configFile = new File("./src/main/resources/Config.json");
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(configFile);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        return (String) jsonObject.get("CargoEntity");
    }

    public String getFlightEntitiesFileName() throws IOException, ParseException{
        File configFile = new File("./src/main/resources/Config.json");
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(configFile);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        return (String) jsonObject.get("FlightEntity");
    }
}
