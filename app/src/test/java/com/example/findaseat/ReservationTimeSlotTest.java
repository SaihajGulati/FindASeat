package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationTimeSlotTest {

    @Test
    public void testReservationTimeSlotTest() {
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.isInitialized());
    }
}