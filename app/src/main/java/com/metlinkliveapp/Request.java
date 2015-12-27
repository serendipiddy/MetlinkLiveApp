package com.metlinkliveapp;

// import org.apache.http.HttpMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

/**
 * Created by iddy on 22/12/2015.
 */
public class Request {
    public static String getPage(Context context, String url) {
        String text;
        try {
            Document doc = Jsoup.connect(url).get();
            text = doc.text();
        }
        catch (IOException e) {
            Log.d(context.getString(R.string.logname),e.getMessage());
            return "Error";
        }
        Log.d("metlinkliveapp",text);
        return text;
    }
}
