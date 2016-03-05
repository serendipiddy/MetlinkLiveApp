package com.metlinkliveapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

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

        serviceID = "1";  // set default service ID


    }

    /**
     * Performs retrieval of the bus locations' JSON file
     * @return
     */
    private Object getBusLocationJSON() {
        Object jsonFile;  // TODO define object type for JSON
        AsyncTask r = new Request().execute(this.busURL);
        try {
            jsonFile = (Object) r.get();
            Log.d("BusLoc", "req-result success");
        }
        catch(Exception e) {
            Log.d("BusLoc", "(Failed to get -- " + e.getClass() + ") " + e.getMessage());
            return null;
        }
        return jsonFile;
    }

    /**
     * Function called when the select bus service button is pressed
     *   Opens a text entry action to enter the service number
     */
    public void selectService(View view) {

    }
}
