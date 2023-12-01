package com.example.findaseat;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.Root;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.view.WindowManager;

@RunWith(AndroidJUnit4.class)
public class ReservationTimeSlotConflictTest {
    private UiDevice device;

    @Before
    public void setUp() {
        MainActivity.setTest();
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void attemptConflictingReservation() {
        // Launch MainActivity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Step 1: Simulate map building selection
            // Assuming building selection via map coordinates (replace with actual UI interaction if available)
            int x = -343; // x-coordinate of marker
            int y = 522; // y-coordinate of marker
            device.click(x, y); // Simulate a tap on the map marker

            // Step 2: Select a time slot in BookingActivity
            onView(withId(R.id.rvTimeSlots))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            // Step 3: Confirm the reservation
            // Replace R.id.confirm_button with the actual ID of the confirmation button
            onView(withText("Reserve Indoor")).perform(click());

            // Verify the Toast message
            onView(withText("You already have a reservation at this time."))
                    .inRoot(new ToastMatcher())
                    .check(matches(isDisplayed()));
        }
    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                // Window token and app token will be the same in the case of a Toast
                return windowToken == appToken;
            }
            return false;
        }
    }

    private void ensureTestUser()
    {
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
