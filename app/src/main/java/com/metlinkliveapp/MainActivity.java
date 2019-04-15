package com.metlinkliveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title bar
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);  // Loads the layout XML file

        QuickLink appState = ((QuickLink)getApplicationContext());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getResources().getAssets().open("routes.txt")));

            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            HashMap<String,String> routes = new HashMap<>();
            ArrayList<String> routeNames = new ArrayList<>();
            HashMap<String,ArrayList<ArrayList<LatLng>>> routesMap = new HashMap<>();
            BufferedReader routeReader;
            ArrayList<ArrayList<LatLng>> routeArray;
            ArrayList<LatLng> pathArray;
            String rLine;
            int index = 0;
            while (mLine != null) {
                String[] route = mLine.split("\t");
                routeNames.add(route[0] + " - " + route[1]);
                routes.put(route[0],route[1]);
                routeReader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getResources().getAssets().open(route[0] + ".txt")));
                rLine = routeReader.readLine();
                Log.d("Main",rLine );
                routeArray = new ArrayList<>();
                while (rLine != null) {
                    pathArray = new ArrayList<>();
                    String[] path = rLine.split(",");

                    for (int i = 0; i < path.length; i = i + 2) {
//                        Log.d("Main","path[" + i + "]: " + path[i] + ". path[i+1]: " + path[i+1] );
                        pathArray.add(new LatLng(Double.parseDouble(path[i]), Double.parseDouble(path[i + 1])));
                    }
                    routeArray.add(pathArray);
                    rLine = routeReader.readLine();
                }
                routesMap.put(route[0],routeArray);
                Log.d("MainActivity","Route " + route[0] + " loaded");
                mLine = reader.readLine();
            }
            appState.setRouteNumberToPathsMap(routesMap);
            appState.setRouteNames(routeNames);
            appState.setRouteNumberToDestinationsMap(routes);
            Log.d("MainActivity","Data loaded");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void openLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void openStopInfo(View view) {
        Intent intent = new Intent(this, StopInfoActivity.class);
        // EditText editText = (EditText) findViewById(R.id.edit_message);
        // String message = editText.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void openBusLocation(View view) {
        Intent intent = new Intent(this, BusLocationActivity.class);
        startActivity(intent);
    }

}
