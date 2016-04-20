package com.metlinkliveapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StopInfoSelectStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info_select_stop);
    }

    public void confirmStopEntry(View view) {
        // check stop is valid
        EditText et = (EditText) findViewById(R.id.stop_info_stop_editText);
        String stopID = et.getText().toString();
        // set current stop == entered stop number
        if (stopID.matches("^\\d{4}$") || stopID.matches("^\\w{4}$")) { // TODO simplify this regex
            // http://stackoverflow.com/a/23024962
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs), MODE_PRIVATE).edit();
            editor.putString("stop_number",stopID);
            editor.commit();
        }
        // else, no change
        finish();
    }
}
