package com.metlinkliveapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class BusLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_location_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bus_map);
        mapFragment.getMapAsync(this);
        Route route = new Route("Island Bay",1);
        routes = new ArrayList<Route>();
        routes.add(route);
        route = new Route("Miramar",2);
        routes.add(route);
        route = new Route("Lyall Bay",3);
        routes.add(route);
        route = new Route("Happy Valley",4);
        routes.add(route);
        route = new Route("Seatoun",5);
        routes.add(route);


        // convert array and display live stop information in GridView
//        List<String> infoString = convertToStringList(infoList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.stop_info_list_item, infoString);
//        GridView lv = (GridView) findViewById(R.id.stop_info_grid_view);
//        lv.setAdapter(adapter);

    }

    public void selectRoute(View view) {
        Intent intent = new Intent(this, RouteSelectActivity.class);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
//        updateList();
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

        QuickLink appState = ((QuickLink)getApplicationContext());
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));

        PolylineOptions options = new PolylineOptions();

        options.color(Color.parseColor("#CC0000FF"));
        options.width(10);
        options.visible(true);
//        options.geodesic(true);//idk what this does

        for (ArrayList<LatLng> list : appState.getIslandBay()) {
            for (LatLng point : list) {
                options.add(point);
            }
            mMap.addPolyline(options);
            options = new PolylineOptions();
        }
//        for (LatLng point : appState.getIslandBayOne()) {
//            options.add(point);
//        }
//
//        mMap.addPolyline(options);
//
//        PolylineOptions optionsTwo = new PolylineOptions();
//
//        options.color(Color.parseColor("#CC0000FF"));
//        options.width(10);
//        options.visible(true);
//
//        for (LatLng point : appState.getIslandBayTwo()) {
//            optionsTwo.add(point);
//        }
//
//        mMap.addPolyline( optionsTwo );
    }

}
