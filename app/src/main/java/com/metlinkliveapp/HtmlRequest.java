package com.metlinkliveapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Arrays;

import android.util.Log;
import android.os.AsyncTask;

/**
 * Created by iddy on 22/12/2015.
 */
public class HtmlRequest extends AsyncTask<String, Void, Document> {
    /* Need to execute network requests in async, not on main thread. AsynkTask<params, progress, result> */
    protected Document doInBackground(String... urls) {
        Document doc = null;
        String url;
        try {
            Log.d("Request_TryInBG", Arrays.toString(urls));
            url = urls[0];
            doc = Jsoup.connect(url).ignoreContentType(true).get();
            Log.d("HtmlRequest","Data: " + doc.outerHtml());
        }
        catch (Exception e) {
            Log.d("Request_doInBG", "Error: (" + e.getClass() + ") -- " + e.getMessage());
        }
        return doc;
    }

}
