package com.example.user_pro.androidtestmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private static final String GOOGLE_API_KEY = "AIzaSyCZ1BCe4Q7YL1nCa_ovtet4Bjn52tT20T8";
    Button button_swt,button_back;
    Context con;
    DBHelper mHelper;
    ArrayList<HashMap<String, String>> MebmerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        con = MapsActivity.this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button_swt = (Button) findViewById(R.id.button_swt);
        button_swt.setOnClickListener(this);
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(this);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_swt:

                Intent intent = new Intent(MapsActivity.this, PlaceList.class);
                startActivity(intent);

                break;

            case R.id.button_back:

                Intent intent2 = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent2);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(intent2);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mHelper = new DBHelper(this);
        DBHelper myDb = new DBHelper(this);
        MebmerList = myDb.SelectAllData();

        if (Geoinfo.getSuccess_type() == 1){
            for (int i=0;i<MebmerList.size();i++){

                MarkerOptions markerOptions = new MarkerOptions();

                LatLng latLng = new LatLng(Double.parseDouble(MebmerList.get(i).get("placeLatiLocal")), Double.parseDouble(MebmerList.get(i).get("placeLongLocal")));
                markerOptions.position(latLng);
                markerOptions.title(MebmerList.get(i).get("placeNameLocal") + " : " + MebmerList.get(i).get("distanceLocal"));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            LatLng now = new LatLng(Geoinfo.getLatigeo(),Geoinfo.getLontigeo());
            mMap.addMarker(new MarkerOptions().position(now).title("NOW HERE").icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(now));
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );

        }else{


            StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrl.append("location=" + Geoinfo.getLatigeo() + "," + Geoinfo.getLontigeo());
            googlePlacesUrl.append("&radius=" + Geoinfo.getRadius());
            googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
            GooglePlacesTask googlePlacesTask = new GooglePlacesTask(con);

            Object[] toPass = new Object[2];
            toPass[0] = googleMap;
            toPass[1] = googlePlacesUrl.toString();
            System.out.println("link : "+ googlePlacesUrl.toString());

            googlePlacesTask.execute(toPass);
        }

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

    };


}
