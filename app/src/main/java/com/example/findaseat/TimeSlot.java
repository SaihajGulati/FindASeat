package com.example.findaseat;

public class TimeSlot {
    private int id;
    private String startTime; // You might want to use a Date object or a custom Time object in a real app
    private String endTime;
    private int indoorSeats;
    private int outdoorSeats;

    public TimeSlot(int id, String startTime, String endTime, int outdoorSeats, int indoorSeats) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.indoorSeats = indoorSeats;
        this.outdoorSeats = outdoorSeats;

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
}
