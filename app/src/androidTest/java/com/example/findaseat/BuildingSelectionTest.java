package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class BuildingSelectionTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBuildingSelected() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

