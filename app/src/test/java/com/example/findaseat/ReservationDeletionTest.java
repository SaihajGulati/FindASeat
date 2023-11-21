package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationDeletionTest {

    @Test
    public void testReservationDeletionTest() {
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.isInitialized());
    }
}