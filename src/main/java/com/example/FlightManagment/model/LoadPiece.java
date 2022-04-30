package com.example.FlightManagment.model;

public class LoadPiece {
    private Integer id;
    private Integer pieces;
    private Weight weight;

    public LoadPiece() {
    }

    public LoadPiece(Integer id, Integer pieces, Weight weight) {
        this.id = id;
        this.pieces = pieces;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPieces() {
        return pieces;
    }

    public Weight getWeight() {
        return weight;
    }

    public Integer getWeightValue(){
        return weight.getWeight();
    }

    public Enum getWeightUnit(){
        return weight.getWeightUnit();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}

