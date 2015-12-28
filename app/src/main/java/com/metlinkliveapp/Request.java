package com.metlinkliveapp;

// import org.apache.http.HttpMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import android.content.Context;
import android.util.Log;
import android.os.AsyncTask;

/**
 * Created by iddy on 22/12/2015.
 */
public class Request extends AsyncTask<String, Void, String> {
    /* Need to execute network requests in async, not on main thread. AsynkTask<params, progress, result> */
    protected String doInBackground(String... urls) {
        String text;
        String url;
        try {
            url = urls[0];
            Document doc = Jsoup.connect(url).get();
            text = doc.text();
        }
        catch (Exception e) {
            // Log.d(context.getString(R.string.logname),e.getMessage());
            return "Error: (" + e.getClass() + ") -- " + e.getMessage();
        }
        // Log.d(context.getString(R.string.logname),text);
        return text;
    }

    protected void onPostExecute(String s) {

    }
}
