package com.example.findaseat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginSuccessTest {

    @Test
    public void testUserNotFound() {

        User loginTry = new User();

        String output = loginTry.login("mantejkhalsa@email.com", "mantej123");

        assertEquals("User logged in.", output);
    }
}
