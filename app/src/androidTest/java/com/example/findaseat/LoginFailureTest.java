package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class LoginFailureTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginFailedProperly() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

