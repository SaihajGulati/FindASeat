package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationModificationTest {

    @Test
    public void testReservationModificationTest() {
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.modifiedSlot("8", "9", "Outdoor", "mantejkhalsa@gmail.com"));
    }
}