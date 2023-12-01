package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationHistoryTest {

    @Test
    public void testReservationHistoryTest() {
        User user = new User();
        assertTrue(user.showReservationHistory("mantejkhalsa@gmail.com"));
    }
}