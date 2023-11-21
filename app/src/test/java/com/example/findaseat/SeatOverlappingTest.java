package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SeatOverlappingTest {

    @Test
    public void testSeatOverlappingTest() {
        Seats seat = new Seats();
        assertTrue(seat.isInitialized());
    }
}