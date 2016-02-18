package com.metlinkliveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class StopInfoActivity extends AppCompatActivity {
    protected StopInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
    }

    /**
     * Called when returning to or opening the activity.
     */
    protected void onResume(){
        super.onResume();
        setStopNumber();
        updateList();
    }

    /**
     * For button use. Launches the activity for selecting a different stop
     */
    public void selectStop(View view) {
        Intent intent = new Intent(this, StopInfoSelectStopActivity.class);
        startActivity(intent);
    }

    /**
     * For button use. Refreshes the current stop's information displayed
     * @param view
     */
    public void refreshLiveInfo(View view) {
        TextView stopNumView = (TextView) findViewById(R.id.stop_info_stop_number);
        // String stop = getString(R.string.default_stop_number);
        String stop = stopNumView.getText().toString();
        updateList();
    }

    /**
     * For button use. Opens activity to filter services
     */
    public void selectFilter(View view) {

    }

    /**
     * Converts List of Departure to List of String
     */
    private List<String> convertToStringList(List<Departure> departureList) {
        List<String> infoString = new LinkedList<String>();

        for (Departure d : departureList) {
            String name = d.getRouteName().substring(0, Math.min(d.getRouteName().length(), 10));
            // infoString.add(d.getRoute()+"\t"+name+"\t"+d.getTimeLeft());
            infoString.add(d.getRoute());
            infoString.add(name);
            infoString.add(d.getTimeLeft());
        }

        return infoString;
    }

    /**
     * Updates the displayed stop information
     */
    private void updateList() {
        // get number from preferences
        SharedPreferences prefs= getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        String stop = prefs.getString("stop_number", null);

        if (stop == null) {
            Log.d("stopinfo_activity","Error: stop number pref is not set before calling updateList()");

            return;
        }
        Log.d("stopinfo_activity", "updating stop info (stop " + stop + ")");

        // query metlink
        info = new StopInfo(stop);
        List<Departure> infoList = info.getInfo();

        // convert array and display live stop information in GridView
        List<String> infoString = convertToStringList(infoList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.stop_info_list_item, infoString);
        GridView lv = (GridView) findViewById(R.id.stop_info_grid_view);
        lv.setAdapter(adapter);
    }

    /**
     * Sets the activity's stop number. Either from preferences or using the default value
     */
    private void setStopNumber() {
        SharedPreferences prefs= getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE);
        String stopNumber = prefs.getString("stop_number", null);

        TextView stopNumView = (TextView) findViewById(R.id.stop_info_stop_number);
        if (stopNumber == null) {
            // set to default
            Log.d("stopinfo_setStopNumber", "stopNumber is null");
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
            editor.putString("stop_number", getString(R.string.default_stop_number));
            editor.commit();
        }
        else if(stopNumView == null) {
            // couldn't find the view.. this shouldn't happen!
            Log.d("stopinfo_setStopNumber","stopNumView is null");
        }
        else {
            Log.d("stopinfo_setStopNumber","setting stopNumView to "+stopNumber);
            stopNumView.setText(stopNumber);
        }
    }


}
