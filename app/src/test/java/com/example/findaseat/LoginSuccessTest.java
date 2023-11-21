package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginSuccessTest {
    @Test
    public void testLoginSuccessTest() {
        // Code to initialize location services and assert it initializes correctly
        LoginSuccessTest newLoginSuccess = new LoginSuccessTest();
        assertTrue(newLoginSuccess.isInitialized());
    }
}
