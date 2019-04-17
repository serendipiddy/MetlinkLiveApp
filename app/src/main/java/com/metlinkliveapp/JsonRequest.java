package com.metlinkliveapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ben_f on 25-Mar-19.
 */
public class JsonRequest extends AsyncTask<String, Void, String> {

    private Context context;

    public JsonRequest(Context context) {
        this.context = context;
    }
    /* Need to execute network requests in async, not on main thread. AsynkTask<params, progress, result> */
    protected String doInBackground(String... urls) {

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(urls[0]);
        Log.e("JsonRequest", "Response from url: " + jsonStr);

        //Reading the bus location data from locally stored file. This is for testing purposes when no buses are running
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(context.getApplicationContext().getResources().getAssets().open("ServiceLocation_1.txt")));
//        } catch (IOException e) {
//            Log.d("JsonRequest", "(Failed to open file -- " + e.getClass() + ") " + e.getMessage());
//            e.printStackTrace();
//        }
//        try {
//            if (reader != null) {
//                jsonStr = reader.readLine();
//            }else {
//                Log.d("JsonRequest","Error: reader = null.");
//                return null;
//            }
//            Log.d("JsonRequest","jsonStr: " + jsonStr);
//        } catch (IOException e) {
//            Log.d("JsonRequest", "(Failed to read from file -- " + e.getClass() + ") " + e.getMessage());
//            e.printStackTrace();
//        }

        //return bus location data
        if (jsonStr != null) {
                return jsonStr;
        } else {
            Log.e("JsonRequest", "Couldn't get json from server.");
        }
        return null;
    }

}
