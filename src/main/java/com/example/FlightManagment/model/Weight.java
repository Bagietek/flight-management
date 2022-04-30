package com.example.FlightManagment.model;

import lombok.Value;

@Value
public class Weight {
    private Integer weight;
    private WeightUnit weightUnit;

    public float ConversionFactor(){
        if (weightUnit == WeightUnit.LB) {
            return (float) 0.45359237;
        }
        return (float) 1.0;
    }
}
