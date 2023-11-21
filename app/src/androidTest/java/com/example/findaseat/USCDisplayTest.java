package com.example.findaseat;

@RunWith(AndroidJUnit4.class)
public class USCDisplayTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void userInfoShown() {
        onView(withId(R.id.mapTab)).check(matches(isDisplayed()));
    }
}

