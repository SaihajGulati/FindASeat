package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MapFragmentTest {
    @Test
    public void testMapFragmentInitialization() {
        // Code to initialize location services and assert it initializes correctly
        MapFragment mapFragment = new MapFragment();
        assertTrue(mapFragment.isInitialized());
    }
}
