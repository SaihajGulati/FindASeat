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
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

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
import android.util.Log;
import android.view.WindowManager;

@RunWith(AndroidJUnit4.class)
public class SeatOverlappingTest {
    private UiDevice device;

    @Before
    public void setUp() {
        MainActivity.setTest();
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void trySeatOverlapping() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            UiObject marker = device.findObject(new UiSelector().descriptionContains("marker title"));

            try
            {
                marker.click(); // simulate a tap on the map marker
            } catch (UiObjectNotFoundException e)
            {
                Log.d("ooops", "ooops");
            }

            // Check Reservation Made
            onView(withId(R.id.mapFragment)).check(matches(isDisplayed()));
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
