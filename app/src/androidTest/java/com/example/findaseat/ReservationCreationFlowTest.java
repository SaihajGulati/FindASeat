package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class ReservationCreationFlowTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void reservationCreated() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

