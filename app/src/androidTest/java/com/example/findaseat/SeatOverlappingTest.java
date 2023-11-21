package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class SeatOverlappingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void noOverlap() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

