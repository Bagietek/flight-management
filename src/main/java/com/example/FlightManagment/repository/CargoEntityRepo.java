package com.example.FlightManagment.repository;

import com.example.FlightManagment.model.LoadEntity;

public interface CargoEntityRepo {
    LoadEntity getAllForFlightId (Integer flightId);
}
