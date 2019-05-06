package com.metlinkliveapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Object mGeoDataClient;
    private boolean mLocationPermissionGranted;
    Button getLocationBtn;
    TextView locationText;
    private HashMap<String,LatLng> nearbyStops = new HashMap<>();

    LocationManager locationManager;
    Location lastKnown;
    Context context;

    Listener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        context = this.getApplicationContext();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
        locListener = new Listener(this);
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 5, locListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void refreshLocation(View view) {
        Log.d("LocationActivity", "refreshLocation()");
//        Location lastKnown = new Location(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
//        if (lastKnown != null) {
////            Log.d("LocationActivity","refreshLocation() setting loc to lastKnown = " + lastKnown.toString());
////            locListener.onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
//        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},0);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager == null) {
            Log.d("LocationActivity","refreshLocation(), locationManager == null");
        }
        if(locListener == null) {
            Log.d("LocationActivity","refreshLocation(), locListener == null");
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locListener, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
                editor.putString("stop_number", marker.getTitle());
                editor.commit();

                if (context == null) {
                    Log.d("LocationActivity","onMarkerClickListener context = null");
                }
                Intent intent = new Intent(context, StopInfoActivity.class);
                startActivity(intent);
                return true;
            }
        });
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                updateStops((cameraPosition.target));
            }
        });
        Log.d("LocationActivity","onMapReady()");
        updateStops(null);
    }

    public void updateStops(LatLng location) {
        if (location != null) {
//            pos = new LatLng(-41.3, 174.78);//fake location to allow stops to draw for testing
            Log.d("LocationActivity","updateStops() with location: " + String.valueOf(location.latitude) + "/" + String.valueOf(location.longitude));
        } else {
            location = new LatLng(-41.3, 174.78);
            Log.d("LocationActivity","updateStops(null) camerPosition= " + mMap.getCameraPosition().toString());
        }
        AsyncTask r = new JsonRequest(this).execute("https://www.metlink.org.nz/api/v1/StopNearby/" + String.valueOf(location.latitude) + "/" + String.valueOf(location.longitude));
        Log.d("LocationActivity","updateStops() with url: https://www.metlink.org.nz/api/v1/StopNearby/" + String.valueOf(location.latitude) + "/" + String.valueOf(location.longitude));
        try {
            String jsonString = (String) r.get();
            Log.d("" +
                    "LocationActivity","updateStops() with jsonString fetched of: " + jsonString);
            JSONArray nearbyStopsJSONArray = new JSONArray(jsonString);
            JSONObject stop;
            for (int i = 0;i < nearbyStopsJSONArray.length();i++) {
                stop = nearbyStopsJSONArray.getJSONObject(i);
                nearbyStops.put(stop.getString("Sms"), new LatLng(Double.parseDouble(stop.getString("Lat")), Double.parseDouble(stop.getString("Long"))));
            }
        } catch (Exception e) {
            Log.d("LocationActivity", "(Failed to get nearby stops with error: " + e.getClass() + ") " + e.getMessage());
        }
        nearbyStops.put("",new LatLng(50.67226388948998,9.551173965398524));

        for (Map.Entry<String,LatLng> stop : nearbyStops.entrySet()) {
            MarkerOptions marker = new MarkerOptions().position(stop.getValue());
            marker.anchor(0.5f, 0.5f);
            marker.title(stop.getKey());
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
            LatLng newLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 11));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-41.3, 174.78), 11));//for testing
            updateStops(newLoc);
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
            Toast.makeText(context, "The current location button will not work without location permissions", Toast.LENGTH_LONG).show();
        }

    }


}
