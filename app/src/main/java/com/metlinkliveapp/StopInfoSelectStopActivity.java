package com.metlinkliveapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class StopInfoSelectStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_info_select_stop_activity);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void confirmStopEntry(View view) {
        // check stop is valid
        EditText et = (EditText) findViewById(R.id.route_editText);
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
