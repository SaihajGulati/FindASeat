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

/*
=======
import com.example.FindASeat.ReservationStatus;
import com.example.findaseat.Date;

import java.util.HashSet;

public class Reservation {
    private int buildingId;
    private Date date;
    private int startTime;
    private int endTime;
    private ReservationStatus status;

    public Reservation() {
        buildingId = -1;
        date = new Date();
        startTime = -1;
        endTime = -1;
        status = com.example.FindASeat.ReservationStatus.ERROR;
    }

    public Reservation(int buildingId, Date date, int startTime, int endTime) {
        this.buildingId = buildingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.ACTIVE;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public com.example.FindASeat.ReservationStatus getStatus() {
        return status;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public static Reservation createReservation(int buildingId, HashSet<Integer> shoppingCart) {
        int startTime = 48, endTime = 0;
        for (Integer time : shoppingCart) {
            startTime = (time < startTime) ? time : startTime;
            endTime = (time > endTime) ? time : endTime;
        }
        if ((endTime - startTime + 1 == shoppingCart.size()) && (endTime-startTime + 1 <= 4)) {
            Date d = new Date(2023, 11, 5, Weekday.MONDAY);
            Reservation r = new Reservation(buildingId, d, startTime, endTime+1);
            return r;
        } else {
            return null;
        }
    }

    public static String intervalString(int startTime, int endTime) {
        int startHr = startTime/2, startMinute = (startTime % 2 == 0) ? 0 : 30,
                endHr = endTime/2, endMinute = (endTime % 2 == 0) ? 0 : 30;
        return String.format("%02d:%02d - %02d:%02d", startHr, startMinute, endHr, endMinute);
    }

}
*/
