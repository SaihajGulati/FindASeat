package com.example.findaseat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginFailureTest {

    @Test
    public void testUserNotFound() {

        FireBaseStuff loginTry = new FireBaseStuff();

        String output = loginTry.login("nonexistent@email.com", "password123");

        assertEquals("User not found. Please check your email.", output);
    }
}
