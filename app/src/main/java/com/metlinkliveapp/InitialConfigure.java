package com.metlinkliveapp;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.RemoteViews;
import android.widget.TextView;


public class InitialConfigure extends ActionBarActivity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_initial_configure);

        ((TextView) findViewById(R.id.stopEntry)).setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 66) {
                    //NewAppWidget.

                    SharedPreferences settings = getPreferences(0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("stop", v.getText().toString());

                    // Commit the edits!
                    editor.commit();

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

        // Restore preferences
        SharedPreferences settings = getPreferences(0);
        String stop;

        while(true) {
            stop = settings.getString("stop", "Fail");
            if (stop.length() > 3) {
                break;
            }
        }

        final Context context = InitialConfigure.this;
        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();


    }



}


