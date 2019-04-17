package com.metlinkliveapp;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ben_f on 21-Mar-19.
 */
public class QuickLink extends Application {

    private HashMap<String,String> routeNumberToDestinationsMap = new HashMap<>();
    private HashMap<String,ArrayList<ArrayList<LatLng>>> routeNumberToPathsMap = new HashMap<>();
    private ArrayList<String> routeNames = new ArrayList<>();

    public QuickLink() {    }

    public ArrayList<String> getRouteNames() {
        return routeNames;
    }

    public void setRouteNames(ArrayList<String> routeNames) {
        this.routeNames = routeNames;
    }

    public void setRouteNumberToDestinationsMap(HashMap routeNumberToDestinationsMap) {
        this.routeNumberToDestinationsMap = routeNumberToDestinationsMap;
    }

    public void addRoute(String number, String name) {
        routeNumberToDestinationsMap.put(number, name);
    }

    public HashMap<String,String> getRouteNumberToDestinationsMap() {
        return routeNumberToDestinationsMap;
    }

    public void setRouteNumberToPathsMap(HashMap<String, ArrayList<ArrayList<LatLng>>> routeNumberToPathsMap) {
        this.routeNumberToPathsMap = routeNumberToPathsMap;
    }

    public HashMap<String,ArrayList<ArrayList<LatLng>>> getRouteNumberToPathsMap() {
        return routeNumberToPathsMap;
    }

    public void putRouteInRoutePaths(String routeNumber, ArrayList<ArrayList<LatLng>> routePaths) {
        this.routeNumberToPathsMap.put(routeNumber, routePaths);
    }

}
