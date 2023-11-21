package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class ReservationHistoryTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void reservationHistoryCorrect() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

