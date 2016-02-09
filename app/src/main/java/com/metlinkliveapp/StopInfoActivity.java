package com.metlinkliveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.LinkedList;
import java.util.List;

public class StopInfoActivity extends AppCompatActivity {
    protected StopInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
        String stop = getString(R.string.current_stop_number);
        updateList(stop);
    }

    /**
     * Converts List of Departure to List of String
     * @param departureList
     * @return
     */
    private List<String> convertToStringList(List<Departure> departureList) {
        List<String> infoString = new LinkedList<String>();

        for (Departure d : departureList) {
            String name = d.getRouteName().substring(0, Math.min(d.getRouteName().length(), 10));
            // infoString.add(d.getRoute()+"\t"+name+"\t"+d.getTimeLeft());
            infoString.add(d.getRoute());
            infoString.add(name);
            infoString.add(d.getTimeLeft());

            Log.d("stopinfo_activity", "converting departure "+d);
        }

        return infoString;
    }

    /**
     * Updates the displayed stop information using the given stop number
     * @param stop
     */
    private void updateList(String stop) {
        Log.d("stopinfo_activity", "updating stop info (stop "+stop+")");
        info = new StopInfo(stop);
        List<Departure> infoList = info.getInfo();
        List<String> infoString = convertToStringList(infoList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.stop_info_list_item, infoString);

        GridView lv = (GridView) findViewById(R.id.stop_info_grid_view);
        lv.setAdapter(adapter);
        Log.d("stopinfo_activity", "Size of info " + infoString.size());
    }

    public void selectStop(View view) {

    }

    public void refreshLiveInfo(View view) {
        String stop = getString(R.string.current_stop_number);
        updateList(stop);
    }

    public void selectFilter(View view) {

    }

}
