package com.example.user_pro.androidtestmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceList extends AppCompatActivity implements View.OnClickListener {

    Context con;

    DBHelper mHelper;
    ArrayList<HashMap<String, String>> MebmerList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placelist_layout);

        mHelper = new DBHelper(this);
        //localdata = mHelper.getLocalDB();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        con = PlaceList.this;

        // get Data from SQLite
        DBHelper myDb = new DBHelper(this);
        MebmerList = myDb.SelectAllData();

        // listView1
        ListView lisView1 = (ListView)findViewById(R.id.list_view);

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(PlaceList.this, MebmerList, R.layout.view_item,
                new String[] {"placeNameLocal", "distanceLocal"}, new int[] {R.id.name_set, R.id.distance_set});
        lisView1.setAdapter(sAdap);

        // OnClick Item
        lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Geoinfo.setSuccess_type(1);
        Intent intent = new Intent(PlaceList.this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            Geoinfo.setSuccess_type(1);
            Intent intent = new Intent(PlaceList.this, MapsActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            /*
            case R.id.button_logout:

                break;
            */

        }
    }
}