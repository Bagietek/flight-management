package com.example.FlightManagment.model;

public class BaseEntity {
    private Integer flightId;

    public BaseEntity() {
    }

    public BaseEntity(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }
}
