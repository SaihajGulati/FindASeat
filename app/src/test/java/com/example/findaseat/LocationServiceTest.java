package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LocationServiceTest {

    @Test
    public void testLocationServicesInitialization() {
        // Code to initialize location services and assert it initializes correctly
        LocationService locationService = new LocationService();
        assertTrue(locationService.isInitialized());
    }
}