package com.metlinkliveapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ben_f on 15-Mar-19.
 */
public class Route {

    private int number;
    private String destination;
    private ArrayList<LatLng> points;

    public void setRoute(ArrayList<LatLng> pointsTo) {
        points = pointsTo;
    }



    public Route(String destination, int number) {
        this.destination = destination;
        this.number = number;
        points = new ArrayList<LatLng>();
    }

}
