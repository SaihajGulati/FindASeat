package com.example.findaseat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ConcurrentHashMap;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private boolean shouldResetCamera = true;

    private static CameraPosition lastCameraPosition;

    public int markerCount = 0;

    int padding;

    LatLngBounds bounds;

    private LatLng marker1;
    private LatLng marker2;
    private LatLng marker3;
    private LatLng marker4;
    private LatLng marker5;
    private LatLng marker6;
    private LatLng marker7;
    private LatLng marker8;
    private LatLng marker9;
    private LatLng marker10;


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

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("mapState")) {
            // If there are arguments and mapState is present, restore the camera position
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(args.getParcelable("mapState")));
            shouldResetCamera = false; // Set to false to prevent unnecessary camera resets
        }

        // Set up map markers
        setUpMapMarkers();

        // Center map on markers if needed
        if (shouldResetCamera) {
            final View mapView = getChildFragmentManager().findFragmentById(R.id.mapFragment).getView();
            if (mapView != null && mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Remove the listener so we don't keep getting callbacks
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                        // Now that the layout is complete, we can safely set the camera to include all markers
                        centerMapOnMarkers();
                    }
                });
            }
        }
    }

    private void setUpMapMarkers() {
        marker1 = new LatLng(34.021865809296685, -118.28290555239819);
        markerCount++;
        marker10 = new LatLng(34.01938251014455, -118.28802783249421);
        markerCount++;
        marker2 = new LatLng(34.022459610740896, -118.2846137481577);
        markerCount++;
        marker3 = new LatLng(34.01885966276545, -118.28236688864008);
        markerCount++;
        marker4 = new LatLng(34.0194553025335, -118.28636741932233);
        markerCount++;
        marker5 = new LatLng(34.0221659806183, -118.28382143281647);
        markerCount++;
        marker6 = new LatLng(34.01883507161909, -118.28427272586752);
        markerCount++;
        marker7 = new LatLng(34.02052886472203, -118.28458389728337);
        markerCount++;
        marker8 = new LatLng(34.02055969234863, -118.28632231747535);
        markerCount++;
        marker9 = new LatLng(34.02034212290399, -118.28366121747526);
        markerCount++;

        int[] mapMarkers = new int[2];
        mapMarkers(mapMarkers);

        mMap.addMarker(new MarkerOptions().position(marker1).title("1"));
        mMap.addMarker(new MarkerOptions().position(marker9).title("9"));
        mMap.addMarker(new MarkerOptions().position(marker2).title("2"));
        mMap.addMarker(new MarkerOptions().position(marker3).title("3"));
        mMap.addMarker(new MarkerOptions().position(marker4).title("4"));
        mMap.addMarker(new MarkerOptions().position(marker5).title("5"));
        mMap.addMarker(new MarkerOptions().position(marker6).title("6"));
        mMap.addMarker(new MarkerOptions().position(marker7).title("7"));
        mMap.addMarker(new MarkerOptions().position(marker8).title("8"));
        mMap.addMarker(new MarkerOptions().position(marker10).title("10"));

    }



    private void centerMapOnMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (bounds == null)
        {
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
            bounds = builder.build();
            padding = 100; // Padding around markers in pixels
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Store the current camera position
        lastCameraPosition = mMap.getCameraPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            if (lastCameraPosition != null) {
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(lastCameraPosition));
            } else {
                centerMapOnMarkers();
            }
        }
    }
    @Override
    public boolean onMarkerClick(Marker marker) {

        // When a marker is clicked, show the BookingFragment
        BookingFragment bookingFragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putString("BUILDING_ID", "b" + marker.getTitle());
        bookingFragment.setArguments(args);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, bookingFragment)
                .addToBackStack(null)
                .commit();

        return true;
    }

    public boolean initializeLocationServices() {
        int markers = 0;
        ConcurrentHashMap<Object, Object> googleMap;
        if (mMap == null) {
            return true;
        }
        for (int i = 0; i < 10; i++){
            ConcurrentHashMap.KeySetView<Object, Object> mapFragment = null;
            googleMap = mapFragment.getMap();
            Object LatLng = null;
            googleMap.get(LatLng);
        }
        return true;
    }

    public boolean mapMarkers (int [] mapMarker) {
        int tempMarkerCount = 0;
        for (int i = 0; i < mapMarker.length; i++){
            tempMarkerCount++;
        }
        return (tempMarkerCount != markerCount);
    }

    public void setMapMarkers(int[] mockMapMarkers) {
    }
}
