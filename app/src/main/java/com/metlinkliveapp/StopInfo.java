package com.metlinkliveapp;

import java.util.LinkedList;
import java.util.List;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by iddy on 28/12/2015.
 */
public class StopInfo {
    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    private String stopName;
    private String stopURL = "https://www.metlink.org.nz/stop/<STOP>/departures";

    public StopInfo(String stopID) {
        // assert stopName.matches("^\\d{4}$"); // doesn't include train stops
        assert stopID.length() == 4;
        assert stopID.matches("^\\d{4}$") || stopID.matches("^\\w{4}$"); // TODO simplify regex
        this.stopName = stopID;
        stopURL = stopURL.replaceAll("<STOP>", stopID);
    }

    /**
     * Retrieves the webpage containing the stop information for this stop.
     * @return Jsoup.Document of page
     */
    private Document getPage() {
        // Retrieve the web page
        Document stopHtml;
        AsyncTask r = new Request().execute(this.stopURL);
        try {
            stopHtml = (Document) r.get();
            Log.d("StopInfo", "req-result success");
        }
        catch(Exception e) {
            Log.d("StopInfo", "(Failed to get -- " + e.getClass() + ") " + e.getMessage());
            return null;
        }
        return stopHtml;
    }

    private List<Departure> processStopInfo(Document doc) {
        List<Departure> list = new LinkedList<Departure>();

        Elements rows = doc.getElementsByTag("table").first().getElementsByTag("tr");
        int dayDelta = 0;
        for (Element tr : rows) {
            Elements tds = tr.getElementsByTag("td");
            if (tds.size() == 4) {
                Departure d = new Departure(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), dayDelta);
                list.add(d);
            }
            else if (tds.text().matches("^\\w{3} \\d{1,2} \\w{3}$")) {
                dayDelta++;
            }
        }

        return list;
    }

    /** Get the live departure information of this stop.
     * @return List<String> containing the list of Departures at this stop
     */
    protected List<Departure> getInfo() {
        Document soup = getPage();
        if (soup == null) {
            Log.d("getInfo", "soup returned null, no stop information available");
            return new LinkedList<Departure>();
        }
        return processStopInfo(soup);
    }
}
