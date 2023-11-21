package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationCreationTest {

    @Test
    public void testReservationCreationTest() {
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.isInitialized());
    }
}