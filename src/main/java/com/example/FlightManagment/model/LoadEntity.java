package com.example.FlightManagment.model;

import java.util.LinkedList;

public class LoadEntity extends BaseEntity{
    LinkedList<Baggage> baggages;
    LinkedList<Cargo> cargos;

    public LoadEntity(Integer flightId, LinkedList<Baggage> baggages, LinkedList<Cargo> cargos) {
        super(flightId);
        this.baggages = baggages;
        this.cargos = cargos;
    }

    public LinkedList<Baggage> getBaggages() {
        return baggages;
    }

    public LinkedList<Cargo> getCargos() {
        return cargos;
    }

}
