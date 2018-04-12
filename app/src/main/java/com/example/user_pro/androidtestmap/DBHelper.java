package com.example.user_pro.androidtestmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mydatabase";

    // Table Name
    private static final String TABLE_MEMBER = "LocalDatabase";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // Create Table Name
        db.execSQL("CREATE TABLE " + TABLE_MEMBER +
                "(MemberID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " latiLocal TEXT(100)," +
                " longLocal TEXT(100)," +
                " radius TEXT(100)," +
                " placeNameLocal TEXT(100)," +
                " placeLatiLocal TEXT(100)," +
                " placeLongLocal TEXT(100)," +
                " distanceLocal TEXT(100));");

        Log.d("CREATE TABLE","Create Table Successfully.");
    }

    // Insert Data
    public long InsertData(String strlati, String strlong, String strrad, String strplacen, String strplacela, String strplacelg, String strdis) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data

            ContentValues Val = new ContentValues();
            Val.put("latiLocal", strlati);
            Val.put("longLocal", strlong);
            Val.put("radius", strrad);
            Val.put("placeNameLocal", strplacen);
            Val.put("placeLatiLocal", strplacela);
            Val.put("placeLongLocal", strplacelg);
            Val.put("distanceLocal", strdis);

            long rows = db.insert(TABLE_MEMBER, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }

    }

    // Show All Data
    public ArrayList<HashMap<String, String>> SelectAllData() {
        // TODO Auto-generated method stub

        try {

            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase();
            // Read Data
            Cursor cursor = db.query
                    (TABLE_MEMBER, null,  "latiLocal = ? AND longLocal = ? AND radius = ?", new String[] { Geoinfo.getLatigeo().toString() , Geoinfo.getLontigeo().toString() , Geoinfo.getRadius().toString() }  , null, null, "placeNameLocal ASC");

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("MemberID", cursor.getString(0));
                        map.put("latiLocal", cursor.getString(1));
                        map.put("longLocal", cursor.getString(2));
                        map.put("radius", cursor.getString(3));
                        map.put("placeNameLocal", cursor.getString(4));
                        map.put("placeLatiLocal", cursor.getString(5));
                        map.put("placeLongLocal", cursor.getString(6));
                        map.put("distanceLocal", cursor.getString(7));
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;

        } catch (Exception e) {
            return null;
        }

    }


    // Select Data
    public String[] SelectData(String strMemberID) {
        // TODO Auto-generated method stub

        try {
            String arrData[] = null;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data



            Cursor cursor = db.query(TABLE_MEMBER, new String[] { "*" },
                    "MemberID=?",
                    new String[] { String.valueOf(strMemberID) }, null, null, null, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    arrData = new String[cursor.getColumnCount()];
                    /***
                     *  0 = MemberID
                     *  1 = Name
                     *  2 = Tel
                     */
                    arrData[0] = cursor.getString(0);
                    arrData[1] = cursor.getString(1);
                    arrData[2] = cursor.getString(2);
                }
            }
            cursor.close();
            db.close();
            return arrData;

        } catch (Exception e) {
            return null;
        }

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);

        // Re Create on method  onCreate
        onCreate(db);
    }


    /*
    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, DbLocal.DATABASE_NAME, null, DbLocal.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIEND_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                DbLocal.TABLE,
                DbLocal.Column.ID,
                DbLocal.Column.LATILOCAL,
                DbLocal.Column.LONGLOCAL,
                DbLocal.Column.RADIUS,
                DbLocal.Column.PLACENAMELOCAL,
                DbLocal.Column.PLACELATILOCAL,
                DbLocal.Column.PLACELONGLOCAL,
                DbLocal.Column.DISTANCELOCAL);

        Log.i(TAG, CREATE_FRIEND_TABLE);

        // create friend table
        db.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + DbLocal.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

    public void addLocalDB(DbLocal dbLocal) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put(DbLocal.Column.LATILOCAL, dbLocal.getLatiLocal());
        values.put(DbLocal.Column.LONGLOCAL, dbLocal.getLongLocal());
        values.put(DbLocal.Column.RADIUS, dbLocal.getRadius());
        values.put(DbLocal.Column.PLACENAMELOCAL, dbLocal.getPlaceNameLocal());
        values.put(DbLocal.Column.PLACELATILOCAL, dbLocal.getPlaceLatiLocal());
        values.put(DbLocal.Column.PLACELONGLOCAL, dbLocal.getPlaceLongLocal());
        values.put(DbLocal.Column.DISTANCELOCAL, dbLocal.getDistanceLocal());

        sqLiteDatabase.insert(DbLocal.TABLE, null, values);

        sqLiteDatabase.close();
    }

    // Insert Data
    public long InsertData(String strMemberID, String strName, String strTel) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data



            ContentValues Val = new ContentValues();
            Val.put("MemberID", strMemberID);
            Val.put("Name", strName);
            Val.put("Tel", strTel);

            long rows = db.insert(DbLocal.TABLE, null, Val);

            db.close();
            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }

    }

    // Show All Data
    public ArrayList<HashMap<String, String>> SelectAllData() {
        // TODO Auto-generated method stub

        try {

            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT  * FROM " + DbLocal.TABLE + "WHERE ";
            Cursor cursor = db.rawQuery(strSQL, null);

            if(cursor != null)
            {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("MemberID", cursor.getString(0));
                        map.put("Name", cursor.getString(1));
                        map.put("Tel", cursor.getString(2));
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;

        } catch (Exception e) {
            return null;
        }

    }


    public List<String> getLocalDB() {

        List<String> loadlocal = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (DbLocal.TABLE, null, DbLocal.Column.LATILOCAL + " = ? AND " + DbLocal.Column.LONGLOCAL + " = ? AND " + DbLocal.Column.RADIUS, new String[] { Geoinfo.getLatigeo().toString() , Geoinfo.getLontigeo().toString() , Geoinfo.getRadius().toString() }  , null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            loadlocal.add(cursor.getLong(0) + "/" +
                    cursor.getString(1) + "/" +
                    cursor.getString(2)+ "/" +
                    cursor.getString(3)+ "/" +
                    cursor.getString(4)+ "/" +
                    cursor.getString(5)+ "/" +
                    cursor.getString(6)+ "/" +
                    cursor.getString(7));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return loadlocal;


    }
    */
}
