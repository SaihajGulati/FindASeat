package com.example.findaseat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MultipleActiveReservationsTest {

    @Test
    public void testMultipleActiveReservationsTest() {
        User timeSlot = new User();
        Register reservation = new Register();
        reservation.reservationTest("mantejkhalsa@gmail.com", "Taper Hall", "Outside", "12", "12:30");
        reservation.reservationTest("mantejkhalsa@gmail.com", "Taper Hall", "Outside", "12", "12:30");


        assertFalse(timeSlot.multipleReservations("mantejkhalsa@gmail.com"));
    }
}
