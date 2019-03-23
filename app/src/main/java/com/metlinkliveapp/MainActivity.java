package com.metlinkliveapp;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.metlinkliveapp.QuickLink;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title bar
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);  // Loads the layout XML file

        QuickLink appState = ((QuickLink)getApplicationContext());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getApplicationContext().getResources().getAssets().open("islandbay.txt")));

            // do reading, usually loop until end of file reading
            StringBuilder sb = new StringBuilder();
            String mLine = reader.readLine();//
            while (mLine != null) {
                String cvsSplitBy = ",";
                String[] route = mLine.split(cvsSplitBy);
                ArrayList<LatLng> pathPoints = new ArrayList<LatLng>();
                for (int i = 0; i < route.length; i = i + 2) {
//                Log.d("main_activity","route[" + i + "] = " + route[i]);
//                Log.d("main_activity","route[" + 1+i + "] = " + route[i+1]);
//                Log.d("main_activity", "route[i] = " + route[i]);
                    pathPoints.add(new LatLng(Double.parseDouble(route[i]), Double.parseDouble(route[i + 1])));
//                    Log.d("main_activity", "Added: " + route[i] + ", " + route[i + 1] + " to routeList");
                }
                appState.addPathToIslandBay(pathPoints);
                mLine = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
