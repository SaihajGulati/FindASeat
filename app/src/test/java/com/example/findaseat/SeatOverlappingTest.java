package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SeatOverlappingTest {

    @Test
    public void testSeatOverlappingTest() {
        TimeSlot seat = new TimeSlot();
        seat.add(1, "mantejkhalsa@gmail.com");
        seat.add(2, "mantejkhalsa@gmail.com");
        assertTrue(seat.overlap("mantejkhalsa@gmail.com"));
    }
}