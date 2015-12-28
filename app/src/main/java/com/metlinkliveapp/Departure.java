package com.metlinkliveapp;

/**
 * Created by iddy on 28/12/2015.
 */
class Departure {
    private String route;
    private String routeName;
    private String timeDeparts;
    private int dayDelta;  // 0: today, 1: tomorrow etc
    public static final String[] day = new String[]{"today", "tomorrow", "2 days", "3 days", "4 days"};

    public Departure (String route, String routeName, String timeDeparts, int dayDelta) {
        this.route = route;
        this.routeName = routeName;
        this.timeDeparts = timeDeparts;
        this.dayDelta = dayDelta;
    }

    public String getRoute() {
        return this.route;
    }

    public String getRouteName() {
        return this.routeName;
    }

    public String getTimeLeft() {
        return this.timeDeparts;
    }

    public int getDayDelta() {
        return this.dayDelta;
    }

    public String toString() {
        return this.route+" "+this.routeName+" "+this.timeDeparts+" "+this.day[this.dayDelta];
    }
}