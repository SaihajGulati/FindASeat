package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class ReservationTimeSlotConflict {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void timeSlotConflict() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

