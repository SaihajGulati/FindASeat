package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class LogoutTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loggedOut() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

