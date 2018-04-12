package com.example.user_pro.androidtestmap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class GooglePlacesTask extends AsyncTask<Object, Integer, String> {
    String googlePlacesData = null;
    GoogleMap googleMap;

    private DBHelper mHelper;

    private Context mContext;

    public GooglePlacesTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Object... inputObj) {
        try {
            googleMap = (GoogleMap) inputObj[0];
            String googlePlacesUrl = (String) inputObj[1];
            Http http = new Http();
            googlePlacesData = http.read(googlePlacesUrl);
        } catch (Exception e) {
            Log.d("Google Place Read Task", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {


        PlacesTask placesDisplayTask = new PlacesTask(mContext);
        Object[] toPass = new Object[2];
        toPass[0] = googleMap;
        toPass[1] = result;
        placesDisplayTask.execute(toPass);
    }
}