package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginFailureTest {
    @Test
    public void testLoginFailureTest() {
        // Code to initialize location services and assert it initializes correctly
        LoginFailure newLoginFailure = new LoginFailure();
        assertTrue(newLoginFailure.isInitialized());
    }
}
