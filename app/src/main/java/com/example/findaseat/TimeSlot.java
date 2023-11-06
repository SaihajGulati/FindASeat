package com.example.findaseat;

public class TimeSlot {
    private String id;
    private String startTime; // You might want to use a Date object or a custom Time object in a real app
    private String endTime;
    private int availableSeats;

    public TimeSlot(String id, String startTime, String endTime, int availableSeats) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableSeats = availableSeats;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
