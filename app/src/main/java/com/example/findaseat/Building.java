package com.example.findaseat;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class Building implements Serializable {
    private String buildingID;
    private String buildingName;
    private String description;
    private int numberOfSeats;
    private int openTime; // e.g., 800 for 8:00 AM
    private int closeTime; // e.g., 1700 for 5:00 PM
    private Map<String, Integer> timeSlots = new HashMap<>(); // Store time slot and available seats
    private Map<String, String> userBookings = new HashMap<>(); // Store user ID and their booking time slot



    // No-argument constructor required by Firebase
    public Building() {
        // Set default values if necessary
    }


    // Updated Building class constructor
    public Building(String buildingID, String buildingName, String description, int numberOfSeats, int openTime, int closeTime) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.description = description;
        this.numberOfSeats = numberOfSeats;
        this.openTime = openTime;
        this.closeTime = closeTime;
        generateTimeSlots(); // Generate time slots based on open and close times
    }

    // Initialize or regenerate the time slots for the building
    private void generateTimeSlots() {
        timeSlots.clear();
        int time = openTime;
        while (time < closeTime) {
            String slot = timeToString(time);
            timeSlots.put(slot, numberOfSeats);
            time = incrementTime(time, 30); // Increment by 30 minutes, adjust as needed
        }
    }

    // Helper method to increment time
    private int incrementTime(int time, int minutesToAdd) {
        int hours = time / 100;
        int minutes = time % 100;
        minutes += minutesToAdd;
        while (minutes >= 60) {
            minutes -= 60;
            hours += 1;
        }
        return hours * 100 + minutes;
    }

    // Helper method to convert time to string
    private String timeToString(int time) {
        return String.format("%04d", time);
    }

    // Book a seat in a time slot for a user
    public boolean bookSeat(String userId, String timeSlot) {
        // Check if the user already has a booking
        if (userBookings.containsKey(userId)) {
            return false; // User already has a booking
        }

        // Check if the time slot exists and has available seats
        if (timeSlots.containsKey(timeSlot) && timeSlots.get(timeSlot) > 0) {
            // Book a seat
            timeSlots.put(timeSlot, timeSlots.get(timeSlot) - 1);
            userBookings.put(userId, timeSlot);
            return true; // Booking successful
        }

        return false; // Booking failed
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

    public String getId() {
        return buildingID;
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
