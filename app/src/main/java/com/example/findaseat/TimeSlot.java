package com.example.findaseat;

public class TimeSlot {

    private String time;
    private boolean isAvailable;

    // Constructor
    public TimeSlot(String time, boolean isAvailable) {
        this.time = time;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // toString Method
    @Override
    public String toString() {
        return "Time: " + (time != null ? time : "Not Set") + ", Available: " + isAvailable;
    }
}
