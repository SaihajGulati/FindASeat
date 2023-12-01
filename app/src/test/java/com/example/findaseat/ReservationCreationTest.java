package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReservationCreationTest {

    @Test
    public void testReservationCreationTest() {
        Register reservation = new Register();

        assertTrue(reservation.reservationTest("mantejkhalsa@gmail.com", "Taper Hall", "Outside", "12", "12:30"));
    }
}