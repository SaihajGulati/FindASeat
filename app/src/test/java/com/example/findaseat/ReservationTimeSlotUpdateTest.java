package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationTimeSlotUpdateTest {
    @Test
    public void testReservationTimeSlotUpdateTest() {
        // Code to initialize location services and assert it initializes correctly
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.isInitialized());
    }
}
