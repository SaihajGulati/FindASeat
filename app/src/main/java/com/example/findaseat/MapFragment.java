package com.example.findaseat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

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
        mMap.addMarker(new MarkerOptions().position(marker1).title("Marker 1"));
        mMap.addMarker(new MarkerOptions().position(marker2).title("Marker 2"));
        mMap.addMarker(new MarkerOptions().position(marker3).title("Marker 3"));
        mMap.addMarker(new MarkerOptions().position(marker4).title("Marker 4"));
        mMap.addMarker(new MarkerOptions().position(marker5).title("Marker 5"));
        mMap.addMarker(new MarkerOptions().position(marker6).title("Marker 6"));
        mMap.addMarker(new MarkerOptions().position(marker7).title("Marker 7"));
        mMap.addMarker(new MarkerOptions().position(marker8).title("Marker 8"));
        mMap.addMarker(new MarkerOptions().position(marker9).title("Marker 9"));
        mMap.addMarker(new MarkerOptions().position(marker10).title("Marker 10"));

        // Use a `ViewTreeObserver` to ensure that the map layout has occurred
        View mapView = getView();
        if (mapView != null) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(marker1);
                    builder.include(marker2);
                    builder.include(marker3);
                    builder.include(marker4);
                    builder.include(marker5);
                    builder.include(marker6);
                    builder.include(marker7);
                    builder.include(marker8);
                    builder.include(marker9);
                    builder.include(marker10);
                    LatLngBounds bounds = builder.build();
                    int padding = 100; // Padding around markers in pixels
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

                    // Remove the listener to avoid multiple calls
                    mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
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
                intent.putExtra("BUILDING_ID", "marker" + (i+1));
                // Start the activity
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
}
