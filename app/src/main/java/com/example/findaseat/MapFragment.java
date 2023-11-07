package com.example.findaseat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);

        // Create LatLng objects for the markers
        LatLng marker1 = new LatLng(34.021865809296685, -118.28290555239819);
        LatLng marker2 = new LatLng(34.01938251014455, -118.28802783249421);
        LatLng marker3 = new LatLng(34.022459610740896, -118.2846137481577);
        LatLng marker4 = new LatLng(34.01885966276545, -118.28236688864008);
        LatLng marker5 = new LatLng(34.0194553025335, -118.28636741932233);
        LatLng marker6 = new LatLng(34.0221659806183, -118.28382143281647);
        LatLng marker7 = new LatLng(34.01883507161909, -118.28427272586752);
        LatLng marker8 = new LatLng(34.02052886472203, -118.28458389728337);
        LatLng marker9 = new LatLng(34.02055969234863, -118.28632231747535);
        LatLng marker10 = new LatLng(34.02034212290399, -118.28366121747526);

        // Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions().position(marker1).title("Marker 1");
        MarkerOptions markerOptions2 = new MarkerOptions().position(marker2).title("Marker 2");
        MarkerOptions markerOptions3 = new MarkerOptions().position(marker3).title("Marker 3");
        MarkerOptions markerOptions4 = new MarkerOptions().position(marker4).title("Marker 4");
        MarkerOptions markerOptions5 = new MarkerOptions().position(marker5).title("Marker 5");
        MarkerOptions markerOptions6 = new MarkerOptions().position(marker6).title("Marker 6");
        MarkerOptions markerOptions7 = new MarkerOptions().position(marker7).title("Marker 7");
        MarkerOptions markerOptions8 = new MarkerOptions().position(marker8).title("Marker 8");
        MarkerOptions markerOptions9 = new MarkerOptions().position(marker9).title("Marker 9");
        MarkerOptions markerOptions10 = new MarkerOptions().position(marker10).title("Marker 10");

        Marker marker1New = mMap.addMarker(markerOptions1);
        Marker marker2New = mMap.addMarker(markerOptions2);
        Marker marker3New = mMap.addMarker(markerOptions3);
        Marker marker4New = mMap.addMarker(markerOptions4);
        Marker marker5New = mMap.addMarker(markerOptions5);
        Marker marker6New = mMap.addMarker(markerOptions6);
        Marker marker7New = mMap.addMarker(markerOptions7);
        Marker marker8New = mMap.addMarker(markerOptions8);
        Marker marker9New = mMap.addMarker(markerOptions9);
        Marker marker10New = mMap.addMarker(markerOptions10);

        // Move the camera to a position that shows all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(marker1New.getPosition());
        builder.include(marker2New.getPosition());
        builder.include(marker3New.getPosition());
        builder.include(marker4New.getPosition());
        builder.include(marker5New.getPosition());
        builder.include(marker6New.getPosition());
        builder.include(marker7New.getPosition());
        builder.include(marker8New.getPosition());
        builder.include(marker9New.getPosition());
        builder.include(marker10New.getPosition());

        LatLngBounds bounds = builder.build();
        int padding = 100; // Padding around markers in pixels
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("TEST", "clicked");
        //instantiate an intent for the BookingActivity
        Context context = requireContext();

        Intent intent = new Intent(context, BookingActivity.class);

        for (int i = 0; i < 10; i++)
        {
            String target = "Marker " + (i+1);
            if (marker.getTitle().equals(target))
            {
                intent.putExtra("BUILDING_ID", "b" + (i+1));
                // Start the activity
                startActivity(intent);
                return true;
            }
        }
        return false;

    }
}
