package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationTimeSlotUpdateTest {
    @Test
    public void testReservationTimeSlotUpdateTest() {
        TimeSlot timeSlot = new TimeSlot();
        assertTrue(timeSlot.update("9", "9:30", "mantejkhalsa@gmail.com"));
    }
}
