package com.example.findaseat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BuildingSelectionTest {
    @Test
    public void testBuildingSectionInitialization() {
        BuildingSection buildingSection = new BuildingSection();
        assertTrue(buildingSection.isInitialized());
    }
}
