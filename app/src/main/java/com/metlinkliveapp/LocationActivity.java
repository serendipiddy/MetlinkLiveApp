package com.metlinkliveapp;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.location.Location;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.GeoDataClient;
//import com.google.android.gms.location.places.PlaceDetectionClient;
//import com.google.android.gms.location.places.PlaceLikelihood;
//import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationActivity extends FragmentActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Object mGeoDataClient;
    private boolean mLocationPermissionGranted;
    Button getLocationBtn;
    TextView locationText;
    private ArrayList<LatLng> nearbyStops = new ArrayList<>();

    LocationManager locationManager;

    Listener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
        locListener = new Listener(this);
        getLocation();
    }

    void getLocation() {
        Log.d("LocationActivity","getLocation()");
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 5, locListener);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
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
//    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));
        Log.d("LocationActivity","onMapReady()");
        updateStops(null);
    }

    public void updateStops(Location location) {
        LatLng pos;
        if (location != null) {
            pos = new LatLng(location.getLatitude(),location.getLongitude());
//            pos = new LatLng(-41.3, 174.78);//fake location to allow stops to draw for testing
            Log.d("LocationActivity","updateStops() with location: " + String.valueOf(location.getLatitude()) + "/" + String.valueOf(location.getLongitude()));
        } else {
            pos = new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
        }
        AsyncTask r = new JsonRequest(this).execute("https://www.metlink.org.nz/api/v1/StopNearby/" + String.valueOf(pos.latitude) + "/" + String.valueOf(pos.longitude));
        Log.d("LocationActivity","updateStops() with url: https://www.metlink.org.nz/api/v1/StopNearby/" + String.valueOf(pos.latitude) + "/" + String.valueOf(pos.longitude));
        try {
            String jsonString = (String) r.get();
            Log.d("LocationActivity","updateStops() with jsonString fetched of: " + jsonString);
            JSONArray nearbyStopsJSONArray = new JSONArray(jsonString);
            JSONObject stop;
            for (int i = 0;i < nearbyStopsJSONArray.length();i++) {
                stop = nearbyStopsJSONArray.getJSONObject(i);
                nearbyStops.add(new LatLng(Double.parseDouble(stop.getString("Lat")),Double.parseDouble(stop.getString("Long"))));
            }
        } catch (Exception e) {
            Log.d("LocationActivity", "(Failed to get nearby stops with error: " + e.getClass() + ") " + e.getMessage());
        }

        for (LatLng stop : nearbyStops) {
            MarkerOptions marker = new MarkerOptions().position(stop);
            marker.anchor(0.5f, 0.5f);
            marker.icon(BitmapDescriptorFactory.fromAsset("theBlueCircle.png"));
            marker.flat(true);
            mMap.addMarker(marker);
        }
    }

    public class Listener implements LocationListener {

        private Context context;

        public Listener (Context context) {
            this.context = context;
        }

        @Override
        public void onLocationChanged(Location location) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 11));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));//for testing
            updateStops(location);
            Log.d("LocationActivity", "onLocationChanged() with location: " + location.toString());
//        locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(context, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        }

    }


}
