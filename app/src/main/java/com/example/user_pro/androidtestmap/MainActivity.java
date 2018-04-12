package com.example.user_pro.androidtestmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    EditText editText_setlati, editText_radius, editText_setlong;
    Button button_searchplace;

    Context con;

    DBHelper mHelper;
    ArrayList<HashMap<String, String>> MebmerList;
    LatLng latLng;

    DecimalFormat numberFormat = new DecimalFormat("#.000");
    RadioButton radioButton_text, radioButton_map;

    LinearLayout linemap, linetext;

    private GoogleMap mMap;
    private static final String GOOGLE_API_KEY = "AIzaSyCZ1BCe4Q7YL1nCa_ovtet4Bjn52tT20T8";

    Integer success = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = MainActivity.this;

        int PERMISSION_ALL = 1;

        String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        mHelper = new DBHelper(this);
        final DBHelper myDb = new DBHelper(this);

        radioButton_text = (RadioButton) findViewById(R.id.radioButton_text);
        radioButton_map = (RadioButton) findViewById(R.id.radioButton_map);

        linemap = (LinearLayout) findViewById(R.id.linemap);
        linetext = (LinearLayout) findViewById(R.id.linetext);

        editText_setlati = (EditText) findViewById(R.id.editText_setlati);
        editText_setlong = (EditText) findViewById(R.id.editText_setlong);
        editText_radius = (EditText) findViewById(R.id.editText_radius);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapmain);
        mapFragment.getMapAsync(this);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        button_searchplace = (Button) findViewById(R.id.button_searchplace);
        button_searchplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(editText_setlati.getText().toString())){
                    double value = Double.parseDouble(editText_setlati.getText().toString());
                    Geoinfo.setLatigeo(Double.parseDouble(numberFormat.format(value)));
                }
                if (!TextUtils.isEmpty(editText_setlong.getText().toString())){
                    double value2 = Double.parseDouble(editText_setlong.getText().toString());
                    Geoinfo.setLontigeo(Double.parseDouble(numberFormat.format(value2)));
                }

                if (!TextUtils.isEmpty(editText_setlati.getText().toString())) {
                    if (!TextUtils.isEmpty(editText_setlong.getText().toString())) {
                        if (!TextUtils.isEmpty(editText_radius.getText().toString())) {

                            Geoinfo.setRadius(Integer.parseInt(editText_radius.getText().toString()));

                            MebmerList = myDb.SelectAllData();

                            for (int i=0;i<MebmerList.size();i++){


                                if (Geoinfo.getLatigeo().toString().equals(MebmerList.get(i).get("latiLocal")) &&
                                        Geoinfo.getLontigeo().toString().equals(MebmerList.get(i).get("longLocal"))
                                        && Geoinfo.getRadius().toString().equals(MebmerList.get(i).get("radius"))){

                                    success = 1;

                                }
                            }

                            if (success == 1){
                                Geoinfo.setSuccess_type(success);
                            }else {
                                Geoinfo.setSuccess_type(success);
                            }

                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(con, "Please enter radius", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(con, "Please enter latitude or drag in map", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(con, "Please enter longitude or drag in map", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent2);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //GPSTracker gpsTracker = new GPSTracker(this);
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(18.7806546,99.0042929)));
        /*
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8))
                .title("Right Here !!"));
        */
        mMap.setOnMapClickListener(this);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton_text:
                if (checked)

                    Geoinfo.setLatigeo(null);
                    Geoinfo.setLontigeo(null);
                    Geoinfo.setRadius(null);

                    editText_setlati.setText("");
                    editText_setlong.setText("");
                    mMap.clear();

                    linemap.setVisibility(View.INVISIBLE);
                    linetext.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButton_map:
                if (checked)

                    Geoinfo.setLatigeo(null);
                    Geoinfo.setLontigeo(null);
                    Geoinfo.setRadius(null);


                    linemap.setVisibility(View.VISIBLE);
                    linetext.setVisibility(View.INVISIBLE);
                break;
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

    @Override
    public void onMapClick(LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        Double mlati = latLng.latitude;
        Double mlong = latLng.longitude;

        Geoinfo.setLatigeo(Double.parseDouble(numberFormat.format(mlati)));
        Geoinfo.setLontigeo(Double.parseDouble(numberFormat.format(mlong)));

        editText_setlati.setText(mlati.toString());
        editText_setlong.setText(mlong.toString());
        // Clears the previously touched position
        mMap.clear();

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
