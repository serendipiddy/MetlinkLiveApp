package com.metlinkliveapp;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;


public class InitialConfigure extends AppCompatActivity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.initial_configure_activity);

        ((TextView) findViewById(R.id.stopEntry)).setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @SuppressLint("CommitPrefEdits")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                final Context context = InitialConfigure.this; never used so commented out

                if (actionId == EditorInfo.IME_ACTION_DONE) {//enter button
                    //NewAppWidget.

                    SharedPreferences settings = getSharedPreferences("widget_pref", MODE_PRIVATE);//get permanently stored preferences
                    SharedPreferences.Editor editor = settings.edit();//get editor
                    editor.putString("stop", v.getText().toString());//put current text in editor
                    Log.i("InitialConfig", v.getText().toString());

                    // Commit the edits!
                    editor.commit();

                    // Push widget update to surface with newly set prefix
//                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context); //never used so commented out

                    // Make sure we pass back the original appWidgetId
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();

                    return true;
                }
                else {
                    return false;
                }
            }
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

    }



}


