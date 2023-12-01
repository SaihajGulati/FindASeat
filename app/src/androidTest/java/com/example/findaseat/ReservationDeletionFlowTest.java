package com.example.findaseat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.pressBack;

import android.content.Context;
import android.content.SharedPreferences;

@RunWith(AndroidJUnit4.class)
public class ReservationDeletionFlowTest {

    @Before
    public void setUp() {
        MainActivity.setTest();
    }

    @Test
    public void deleteReservationFlow() {

        // Launch MainActivity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate to the ProfileFragment
            onView(withId(R.id.menu_profile)).perform(click());

            // Navigate to the ReservationsFragment from ProfileFragment
            onView(withId(R.id.showReservationsButton)).perform(click());

            // Select a reservation to modify
            onView(withId(R.id.rvTimeSlots)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            // click cancel button
            onView(withText("Cancel")).perform(click());

            // Verify if back at the main map screen
            onView(withId(R.id.mapFragment)).check(matches(isDisplayed()));
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
