package com.metlinkliveapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StopInfoSelectStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_info_select_stop_activity);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        QuickLink appState = ((QuickLink)getApplicationContext());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,android.R.layout.select_dialog_item,appState.getRouteNames());
        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.stopTextView);
        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);
        actv.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stopName = actv.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
                stopName = stopName.split(" ")[0];
                Log.d("RouteSelect", "stopName: " + stopName);
                editor.putString("stop_number", stopName);
                editor.commit();
                finish();
            }
        });
        actv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2 && before < count) {
                    Log.d("StopInfoSelect","before: " + Integer.valueOf(before)+ " count: " + count + "s.length: " + s.length());
                    for (String stop : getSuggestedStops(s.toString())) {
                        Log.d("StopInfoSelect",stop);
                    }
                    actv.setAdapter(new ArrayAdapter<String>
                            (actv.getContext(), android.R.layout.select_dialog_item, getSuggestedStops(s.toString())));
                    actv.showDropDown();
                } else {
                    actv.setAdapter(new ArrayAdapter<String>
                            (actv.getContext(), android.R.layout.select_dialog_item, new ArrayList<String>()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public ArrayList<String> getSuggestedStops(String text) {
        AsyncTask r = new JsonRequest(this).execute("https://www.metlink.org.nz/api/v1/StopSearch/" + text);
        ArrayList<String> suggestedStopsArray = new ArrayList<>();
        try {
            String jsonString = (String) r.get();
            Log.d("StopInfoSelect",jsonString);
            JSONArray suggestedStopsJSONArray = new JSONArray(jsonString);

            JSONObject stop;
            for (int i = 0;i < suggestedStopsJSONArray.length();i++) {
                stop = suggestedStopsJSONArray.getJSONObject(i);
                suggestedStopsArray.add(stop.getString("Name"));
            }
            return suggestedStopsArray;
//            busInfo = json.getJSONArray("Services");
//            Log.d("BusInfo", "req-result success");
        } catch (Exception e) {
            Log.d("StopInfoSelectStop", "(Failed to get suggested stops with error: " + e.getClass() + ") " + e.getMessage());
        }
        return suggestedStopsArray;
    }

    public void confirmStopEntry(View view) {
        // check stop is valid
        EditText et = (EditText) findViewById(R.id.stopTextView);
        String stopNumber = et.getText().toString();
        // set current stop == entered stop number
        if (stopNumber.matches("^\\d{4}$")) {
            // http://stackoverflow.com/a/23024962
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
            editor.putString("stop_number",stopNumber);
            editor.commit();
        }
        // else, no change
        finish();
    }
}
