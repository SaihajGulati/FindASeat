package com.example.findaseat;

public class Reservation {
    private String userId;
    private String timeSlotId;
    private long reservationTime; // Unix timestamp for when the reservation was made

    public Reservation() {
        // Default constructor required for calls to DataSnapshot.getValue(Reservation.class)
    }

    public Reservation(String userId, String timeSlotId, long reservationTime) {
        this.userId = userId;
        this.timeSlotId = timeSlotId;
        this.reservationTime = reservationTime;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public long getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
    }
}

