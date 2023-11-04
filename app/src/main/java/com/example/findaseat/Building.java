package com.example.findaseat;

import java.io.Serializable;

public class Building implements Serializable {
    private String buildingID;
    private String buildingName;
    private String description;
    private int numberOfSeats;

    public Building() {
    }

    public Building(String buildingID, String buildingName, String description, int numberOfSeats) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.description = description;
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingID='" + buildingID + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", description='" + description + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
