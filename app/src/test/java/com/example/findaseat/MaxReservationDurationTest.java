package com.example.findaseat;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MaxReservationDurationTest {

    @Test
    public void testMaxReservationDurationTest() {
        // Code to initialize location services and assert it initializes correctly
        TimeSlot timeSlot = new TimeSlot();

        assertFalse(timeSlot.reservations(2,3));
    }
}
