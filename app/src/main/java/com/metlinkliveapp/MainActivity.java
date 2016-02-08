package com.metlinkliveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title bar
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Loads the layout XML file
    }

    protected void openLocation(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
    }

    protected void openStopInfo(View view) {

    }

    protected void openBus(View view) {

    }
}
