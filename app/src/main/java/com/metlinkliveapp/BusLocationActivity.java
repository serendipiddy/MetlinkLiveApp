package com.metlinkliveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_location_activity);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bus_map);
        mapFragment.getMapAsync(this);

        //Update selected stop textview
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        String stop = prefs.getString("route_number", null);
        TextView serviceNumber = (TextView)findViewById(R.id.bus_location_service_number);
        serviceNumber.setText(stop);
    }

    public void selectRoute(View view) {
        Intent intent = new Intent(this, RouteSelectActivity.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        String route = prefs.getString("route_number", null);
        if (route != null) {
            TextView serviceNumber = (TextView)findViewById(R.id.bus_location_service_number);
            serviceNumber.setText(route);
            updateMap();
        }

    }

    /**
     * For button use. Refreshes the bus locations displayed
     * @param view
     */
    public void refreshBusInfo(View view) {
        updateMap();
    }

    private void updateMap() {
        if (mMap != null) {
            mMap.clear();
            JSONArray busInfo;
            SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
            String route = prefs.getString("route_number", null);
            if (route != null) {
                //Draw bus route
                PolylineOptions options = new PolylineOptions();

                options.color(Color.parseColor("#CC0000FF"));
                options.width(10);
                options.visible(true);

                QuickLink appState = ((QuickLink) getApplicationContext());

                Log.d("Bus","Route: " + route);
                Log.d("Bus","routeNumberToPathsMap.keySet= " + appState.getRouteNumberToPathsMap().keySet().toString());
                if (appState.getRouteNumberToPathsMap().get(route) == null) {
                    Log.d("Bus", "faillllll");
                }


                for (ArrayList<LatLng> path : appState.getRouteNumberToPathsMap().get(route)) {
                    for (LatLng point : path) {
                        options.add(point);
                    }
                    mMap.addPolyline(options);
                    options = new PolylineOptions();
                }



            }

            //Draw bus markers
            Log.d("BusInfo", "Stop: " + route);
            AsyncTask r = new JsonRequest(this).execute("https://www.metlink.org.nz/api/v1/ServiceLocation/" + route);
            try {
                JSONObject json = (JSONObject) r.get();
                busInfo = json.getJSONArray("Services");
                Log.d("BusInfo", "req-result success");
            } catch (Exception e) {
                Log.d("BusInfo", "(Failed to get -- " + e.getClass() + ") " + e.getMessage());
                return;
            }

            JSONObject bus;
            try {
                for (int i = 0; i < busInfo.length(); i++) {
                    bus = busInfo.getJSONObject(i);
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(bus.getDouble("Lat"), bus.getDouble("Long")));
                    marker.anchor(0.5f, 0.5f);
                    marker.icon(BitmapDescriptorFactory.fromAsset("blueArrow.bmp"));
                    marker.rotation(Float.parseFloat(bus.getString("Bearing")));//0 is up, 90 is right etc
                    marker.flat(true);
                    mMap.addMarker(marker);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));
        updateMap();
    }

}
