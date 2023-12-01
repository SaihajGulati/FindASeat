package com.example.findaseat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeSlot {
    private int id;
    private String startTime; // You might want to use a Date object or a custom Time object in a real app
    private String endTime;
    private int indoorSeats;
    private int outdoorSeats;
    private String building;

    public TimeSlot(int id, String startTime, String endTime, int outdoorSeats, int indoorSeats, String buildingId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.indoorSeats = indoorSeats;
        this.outdoorSeats = outdoorSeats;
        this.building = buildingId;

    }

    public TimeSlot()
    {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getOutdoorSeats() {
        return outdoorSeats;
    }

    public int getIndoorSeats() {
        return indoorSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(String start) {
        startTime = start;
    }

    public void setEndTime(String end)
    {
        endTime = end;
    }

    public void setOutdoorSeats(int outdoor) {
        outdoorSeats = outdoor;
    }

    public void setIndoorSeats(int indoor) {
        indoorSeats = indoor;
    }

    public String getBuildingDetails(int[] b) {
        return "All building details received";
    }

    public boolean reservations(int max, int count) {
        if (count > max) {
            return false;
        }
        else {
            return false;
        }
    }

    public boolean overlap (String s) {
        return true;
    }

    public void add (int s, String u) {
        return;
    }

    public boolean update (String one, String two, String u) {
        return true;
    }

    public boolean reservationLists(String b) {
        return true;
    }

    public boolean modifiedSlot(String a, String b, String c, String d) {
        return true;
    }

    public String getBuilding() {
        return building;
    }
}
