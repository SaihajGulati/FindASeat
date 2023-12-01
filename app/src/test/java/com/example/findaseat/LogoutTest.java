package com.example.findaseat;

import android.content.SharedPreferences;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static android.content.Context.MODE_PRIVATE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogoutTest {

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor editor;

    @Mock
    private FragmentActivity fragmentActivity;

    @Mock
    private FragmentManager fragmentManager;

    @Mock
    private FragmentTransaction fragmentTransaction;

    private ProfileFragment profileFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Mocking SharedPreferences
        when(fragmentActivity.getSharedPreferences("MySharedPrefs", MODE_PRIVATE)).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);

        // Mocking FragmentManager and FragmentTransaction
        when(fragmentActivity.getSupportFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);

        // Mocking the view
        View mockView = mock(View.class);
        when(mockView.getId()).thenReturn(123); // replace with the actual ID
        profileFragment = new ProfileFragment() {
            @Override
            public View getView() {
                return mockView;
            }
        };
    }

    @After
    public void tearDown() {
        // Clear SharedPreferences after each test
        editor.clear().apply();
    }

    @Test
    public void testLogout() {
        // Simulate a logged-in state by setting some data in SharedPreferences
        when(sharedPreferences.getString("usc_id", null)).thenReturn("123456");
        when(sharedPreferences.getString("name", null)).thenReturn("John Doe");
        when(sharedPreferences.getBoolean("loggedIn", false)).thenReturn(true);

        // Call the logout method
        profileFragment.logout();

        // Verify that user data is cleared from SharedPreferences
        verify(editor).clear();
        verify(editor).apply();

        // Verify that the fragment is replaced with LoginFragment
        LoginFragment loginFragment = new LoginFragment();
        verify(fragmentTransaction).replace(Mockito.eq(123), Mockito.eq(loginFragment));
        verify(fragmentTransaction).addToBackStack(null);  // Optional: Add to back stack for navigation history
        verify(fragmentTransaction).commit();
    }
}
