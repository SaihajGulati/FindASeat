package com.example.findaseat;

import java.util.ArrayList;
import java.util.List;

// Assuming Building is a class you've defined to hold your building data
public class BuildingData {

    // Static method to return a list of buildings
    public static List<Building> getBuildings() {
        List<Building> buildings = new ArrayList<>();

        // Adding buildings to the list
        buildings.add(new Building("b1", "Leavey Library", "Library", 330, 800, 1700));
        buildings.add(new Building("b2", "Taper Hall", "Lecture Building", 80, 800, 1700));
        // ... Add more buildings as needed

        return buildings;
    }
}
