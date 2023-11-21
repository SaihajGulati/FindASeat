package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLaunchingAppWithoutLoggingIn() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

