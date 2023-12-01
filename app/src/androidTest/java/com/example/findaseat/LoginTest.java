package com.example.findaseat;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Before
    public void clearSharedPreferences() {
        // Get the context of the app under test
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Clear all data from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Test
    public void appLaunchesOnMapTabWhenNotLoggedIn() {
        // Start the MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Check if the current fragment is MapFragment
        scenario.onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

            if (!(currentFragment instanceof MapFragment)) {
                throw new AssertionError("Current fragment is not MapFragment");
            }
        });
    }
}
