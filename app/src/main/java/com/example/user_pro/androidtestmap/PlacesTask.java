package com.example.user_pro.androidtestmap;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlacesTask extends AsyncTask<Object, Integer, List<HashMap<String, String>>> {

    JSONObject googlePlacesJson;
    GoogleMap mMap;

    private String[] colors = {"#7fff7272"};

    DBHelper mHelper;
    private int ID = -1;

    String serverKey = "AIzaSyCBQcbhYIXmymP45xMYCkEADfisG9MudRk";
    LatLng origin = new LatLng(37.7849569, -122.4068855);
    LatLng destination = new LatLng(37.7814432, -122.4460177);

    private Context mContext;

    public PlacesTask (Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

        List<HashMap<String, String>> googlePlacesList = null;
        PlacesParse placeJsonParser = new PlacesParse();

        try {
            mMap = (GoogleMap) inputObj[0];
            googlePlacesJson = new JSONObject((String) inputObj[1]);
            googlePlacesList = placeJsonParser.parse(googlePlacesJson);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {

        final DBHelper myDb = new DBHelper(mContext);
        //googleMap.clear();
        for (int i = 0; i < list.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = list.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            String test1 = googlePlace.get("types");
            //distance
            Location loc1 = new Location("");
            loc1.setLatitude(Geoinfo.getLatigeo());
            loc1.setLongitude(Geoinfo.getLontigeo());
            Location loc2 = new Location("");
            loc2.setLatitude(lat);
            loc2.setLongitude(lng);

            DecimalFormat numberFormat = new DecimalFormat("#.000");
            float[] result=new float[5];
            Location.distanceBetween (Geoinfo.getLatigeo(),Geoinfo.getLontigeo(),lat,lng,result);
            String d=Float.toString(result[0]/1000);
            //float distanceInKiMeters = String.valueOf(result[0])/1000;

            //Check
            System.out.println("Distance : " + d + " : " + placeName + " : " + vicinity + " : " + test1 );

            //Add to sqlite
            // Save Data
            long saveStatus = myDb.InsertData(Geoinfo.getLatigeo().toString(),
                    Geoinfo.getLontigeo().toString(),
                    Geoinfo.getRadius().toString(),
                    "Nameplace : " + placeName,
                    googlePlace.get("lat"),
                    googlePlace.get("lng"),
                    "Distance : " + d + " km");
            if(saveStatus <=  0)
            {
                System.out.println("ERROR");
            }


            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + d + " : ");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        }

        LatLng now = new LatLng(Geoinfo.getLatigeo(),Geoinfo.getLontigeo());
        mMap.addMarker(new MarkerOptions().position(now).title("NOW HERE").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(now));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
    }

}