package com.example.findaseat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    // Existing code in ProfileFragment

    // Create a method to instantiate ProfileFragment with user data
    public static ProfileFragment newInstance(String uscId, String name, String affiliation) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("usc_id", uscId);
        args.putString("name", name);
        args.putString("affiliation", affiliation);
        fragment.setArguments(args);
        return fragment;
    }
}
