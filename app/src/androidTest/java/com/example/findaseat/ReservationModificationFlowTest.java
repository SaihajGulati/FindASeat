package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class ReservationModificationFlowTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void reservationModified() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

