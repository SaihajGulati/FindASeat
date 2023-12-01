package com.example.findaseat;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.view.WindowManager;

@RunWith(AndroidJUnit4.class)
public class LoginFailureTest {

    @Before
    public void setUp() {
        MainActivity.clear();
    }

    @Test
    public void LoginFail() {
        // Launch MainActivity
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {

            // Wait for the main activity to load
            onView(withId(R.id.fragmentContainer)).check(matches(isDisplayed()));

            // Navigate to the login page
            onView(withId(R.id.menu_profile)).perform(click());

            // Wait for the login page to load
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));

            // Enter incorrect credentials
            onView(withId(R.id.login_email)).perform(typeText("wrong@gmail.com"));
            onView(withId(R.id.login_password)).perform(typeText("wrongpassword"));

            // Click the login button
            onView(withId(R.id.login_button)).perform(click());

            //Verify page did not change, so were not logged in
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        }
    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {
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
                return windowToken == appToken && root.getDecorView().getHeight() > 0;
            }
            return false;
        }
    }

    private void clearUser() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
