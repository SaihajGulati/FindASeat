package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationDeletionTest {

    @Test
    public void testReservationDeletionTest() {
        User user = new User();
        assertTrue(user.deleteReservation("mantejkhalsa@gmail.com", "Taper Hall", "12", "12:30"));
    }
}