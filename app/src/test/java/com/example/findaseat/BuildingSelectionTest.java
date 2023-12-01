package com.example.findaseat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;

public class BuildingSelectionTest {


    @Test
    public void testBuildingSelection() {
        // Arrange
        int[] buildingIDs = {1, 2, 3, 4, 5, 6, 7, 8};

        TimeSlot buildingDetails = new TimeSlot();

        // Act
        String result = buildingDetails.getBuildingDetails(buildingIDs);

        // Assert
        assertEquals("All building details received", result);

    }
}
