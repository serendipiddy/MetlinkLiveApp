package com.metlinkliveapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class RouteSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_select_activity);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        QuickLink appState = ((QuickLink)getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,appState.getRouteNames());
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.stopTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);
        actv.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                confirmStopEntry(view);
            }
        });

//        actv.setX(20);
//        actv.setY(20);

    }

    @SuppressLint("CommitPrefEdits")
    public void confirmStopEntry(View view) {
        // check stop is valid
        AutoCompleteTextView et = (AutoCompleteTextView ) findViewById(R.id.stopTextView);
        String routeNumber = et.getText().toString();
        QuickLink appState = ((QuickLink)getApplicationContext());
        if (appState.getRouteNames().contains(routeNumber)) {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
            routeNumber = routeNumber.split(" ")[0];
            Log.d("RouteSelect", "routeNumber: " + routeNumber);
            editor.putString("route_number", routeNumber);
            editor.commit();
        }

        finish();
    }

}
