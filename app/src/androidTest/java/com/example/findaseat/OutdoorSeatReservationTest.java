package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class outdoorSeatReservationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void outdoorReserved() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

