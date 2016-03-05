package com.metlinkliveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BusLocationActivity extends AppCompatActivity {
    private String serviceID;
    private String busURL = "https://www.metlink.org.nz/api/VehicleLocationRequestJson?route=<service>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_location);
    }

    /**
     * Called when the refresh button is pressed (or the activity is loaded)
     * @param view
     */
    public void refreshBusLocation(View view) {
        


    }

    /**
     * Function called when the select bus service button is pressed
     *   Opens a text entry action to enter the service number
     */
    public void selectService(View view) {

    }
}
