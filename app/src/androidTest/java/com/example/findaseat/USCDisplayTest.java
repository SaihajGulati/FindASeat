package com.example.findaseat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Context;
import android.content.SharedPreferences;

@RunWith(AndroidJUnit4.class)
public class USCDisplayTest {

    @Before
    public void setUp() {
        ensureTestUser();
    }

    @Test
    public void profileTest() {
        // Launch MainActivity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate to the ProfileFragment
            onView(withId(R.id.menu_profile)).perform(click());

            // Check that the name and affiliation are displayed correctly
            onView(withId(R.id.user_name)).check(matches(withText("Name: Mantej")));
            onView(withId(R.id.user_affiliation)).check(matches(withText("Affiliation: undergraduate")));
        }
    }

    private void ensureTestUser() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("usc_id", "12345678");
        editor.putString("name", "Mantej");
        editor.putString("affiliation", "undergraduate");
        editor.putBoolean("loggedIn", true);
        editor.apply();
    }
}
