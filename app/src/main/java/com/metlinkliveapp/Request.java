package com.metlinkliveapp;

// import org.apache.http.HttpMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;
import android.os.AsyncTask;

/**
 * Created by iddy on 22/12/2015.
 */
public class Request extends AsyncTask<String, Void, Document> {
    /* Need to execute network requests in async, not on main thread. AsynkTask<params, progress, result> */
    protected Document doInBackground(String... urls) {
        Document doc = null;
        String url;
        try {
            Log.d("Request_TryInBG", Arrays.toString(urls));
            url = urls[0];
            doc = Jsoup.connect(url).get();
        }
        catch (Exception e) {
            Log.d("Request_doInBG", "Error: (" + e.getClass() + ") -- " + e.getMessage());
        }
        return doc;
    }

    protected void onPostExecute(String s) { }
}
