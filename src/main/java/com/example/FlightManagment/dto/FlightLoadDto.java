package com.example.FlightManagment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FlightLoadDto {
    private Integer totalWeight;
    private Integer baggageWeight;
    private Integer cargoWeight;

}
