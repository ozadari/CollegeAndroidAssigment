package com.restreviewer.restreviewer.DAL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.restreviewer.restreviewer.Restaurant;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by paz on 05/03/2018.
 */

public class LocalRestaurants {
    final static String RESTAURANT_TABLE = "restaurants";
    final static String RESTAURANT_TABLE_ID = "id";
    final static String RESTAURANT_TABLE_NAME = "name";
    final static String RESTAURANT_TABLE_ADDRESS = "address";
    final static String RESTAURANT_TABLE_TYPE = "type";
    final static String RESTAURANT_TABLE_DELIVERIES = "deliveries";
    final static String RESTAURANT_TABLE_KOSHER = "kosher";
    final static String RESTAURANT_TABLE_PHONE = "phone";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + RESTAURANT_TABLE + " (" +
                RESTAURANT_TABLE_ID + " INTEGER PRIMARY KEY," +
                RESTAURANT_TABLE_NAME + " TEXT," +
                RESTAURANT_TABLE_ADDRESS + " TEXT," +
                RESTAURANT_TABLE_TYPE + " TEXT," +
                RESTAURANT_TABLE_DELIVERIES + " BOOLEAN," +
                RESTAURANT_TABLE_PHONE + " TEXT," +
                RESTAURANT_TABLE_KOSHER + " BOOLEAN);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + RESTAURANT_TABLE + ";");
    }

    public static void truncate(SQLiteDatabase db) {
        db.execSQL("delete from " + RESTAURANT_TABLE + ";");
    }

    public static List<Restaurant> getAllRestaurants(SQLiteDatabase db) {
        Cursor cursor = db.query(RESTAURANT_TABLE, null, null , null, null, null, null);
        List<Restaurant> allRestaurants = new LinkedList<Restaurant>();
        allRestaurants.clear();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(RESTAURANT_TABLE_ID);
            int nameIndex = cursor.getColumnIndex(RESTAURANT_TABLE_NAME);
            int addressIndex = cursor.getColumnIndex(RESTAURANT_TABLE_ADDRESS);
            int typeIndex = cursor.getColumnIndex(RESTAURANT_TABLE_TYPE);
            int deliveriesIndex = cursor.getColumnIndex(RESTAURANT_TABLE_DELIVERIES);
            int kosherIndex = cursor.getColumnIndex(RESTAURANT_TABLE_KOSHER);
            int phoneIndex = cursor.getColumnIndex(RESTAURANT_TABLE_PHONE);
            do {
                Integer id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String address = cursor.getString(addressIndex);
                String type = cursor.getString(typeIndex);
                String phone = cursor.getString(phoneIndex);
                int deliveries = cursor.getInt(deliveriesIndex); //0 false / 1 true
                int kosher = cursor.getInt(kosherIndex); //0 false / 1 true
                Restaurant st = new Restaurant(id, name, address, type, kosher  == 1, phone, deliveries  == 1);
                allRestaurants.add(st);
            } while (cursor.moveToNext());
        }
        return allRestaurants;
    }

    public static void addRestaurant(SQLiteDatabase db, Restaurant st) {
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_TABLE_ID, st.getId());
        values.put(RESTAURANT_TABLE_ADDRESS, st.getAddress());
        values.put(RESTAURANT_TABLE_NAME, st.getName());
        values.put(RESTAURANT_TABLE_PHONE, st.getPhone());
        values.put(RESTAURANT_TABLE_TYPE, st.getType());
        if (st.getKosher()) {
            values.put(RESTAURANT_TABLE_KOSHER, 1);
        } else {
            values.put(RESTAURANT_TABLE_KOSHER, 0);
        }
        if (st.getDeliveries()) {
            values.put(RESTAURANT_TABLE_DELIVERIES, 1);
        } else {
            values.put(RESTAURANT_TABLE_DELIVERIES, 0);
        }

        db.insertWithOnConflict(RESTAURANT_TABLE, RESTAURANT_TABLE_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastSyncLocal.getLastUpdate(db,RESTAURANT_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastSyncLocal.setLastUpdate(db,RESTAURANT_TABLE, date);
    }
}
