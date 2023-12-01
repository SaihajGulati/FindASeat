package com.example.findaseat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MapFragmentTest {

    private MapFragment mapFragment;

    @Before
    public void setUp() {
        mapFragment = new MapFragment();
    }

    @Test
    public void testMapMarkers() {
        // Arrange
        int[] mockMapMarkers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        mapFragment.setMapMarkers(mockMapMarkers);

        // Act
        boolean result = mapFragment.mapMarkers(mockMapMarkers);

        // Assert
        assertTrue("Marker count should not match", result);
    }
}
