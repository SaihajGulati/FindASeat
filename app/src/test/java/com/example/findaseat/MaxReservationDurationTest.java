package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MaxReservationDurationTest {

    @Test
    public void testMaxReservationDurationTest() {
        // Code to initialize location services and assert it initializes correctly
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.isInitialized());
    }
}
