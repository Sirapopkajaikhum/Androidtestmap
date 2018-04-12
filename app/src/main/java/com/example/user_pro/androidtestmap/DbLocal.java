package com.example.user_pro.androidtestmap;

import android.provider.BaseColumns;

public class DbLocal {

    //Database
    public static final String DATABASE_NAME = "Local_DBPlace.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "places";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String LATILOCAL = "latiLocal";
        public static final String LONGLOCAL = "longLocal";
        public static final String RADIUS = "radius";
        public static final String PLACENAMELOCAL = "placeNameLocal";
        public static final String PLACELATILOCAL = "placeLatiLocal";
        public static final String PLACELONGLOCAL = "placeLongLocal";
        public static final String DISTANCELOCAL = "distanceLocal";
    }

    private int id;
    private String latiLocal;
    private String longLocal;
    private String radius;
    private String placeNameLocal;
    private String placeLatiLocal;
    private String placeLongLocal;
    private String distanceLocal;

    //Default Constructor
    public DbLocal() {

    }
    //Constructor
    public DbLocal(int id, String latiLocal, String longLocal, String radius, String placeNameLocal,
                  String placeLatiLocal, String placeLongLocal, String distanceLocal) {

        this.id = id;
        this.latiLocal = latiLocal;
        this.longLocal = longLocal;
        this.radius = radius;
        this.placeNameLocal = placeNameLocal;
        this.placeLatiLocal = placeLatiLocal;
        this.placeLongLocal = placeLongLocal;
        this.distanceLocal = distanceLocal;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatiLocal() {
        return latiLocal;
    }

    public void setLatiLocal(String latiLocal) {
        this.latiLocal = latiLocal;
    }

    public String getLongLocal() {
        return longLocal;
    }

    public void setLongLocal(String longLocal) {
        this.longLocal = longLocal;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getPlaceNameLocal() {
        return placeNameLocal;
    }

    public void setPlaceNameLocal(String placeNameLocal) {
        this.placeNameLocal = placeNameLocal;
    }

    public String getPlaceLatiLocal() {
        return placeLatiLocal;
    }

    public void setPlaceLatiLocal(String placeLatiLocal) {
        this.placeLatiLocal = placeLatiLocal;
    }

    public String getPlaceLongLocal() {
        return placeLongLocal;
    }

    public void setPlaceLongLocal(String placeLongLocal) {
        this.placeLongLocal = placeLongLocal;
    }

    public String getDistanceLocal() {
        return distanceLocal;
    }

    public void setDistanceLocal(String distanceLocal) {
        this.distanceLocal = distanceLocal;
    }
}
