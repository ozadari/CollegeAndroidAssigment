package com.restreviewer.restreviewer.DAL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by paz on 05/03/2018.
 */

public class LastSyncLocal {
        final static String LAST_SYNC_TABLE = "last_sync_local";
        final static String LAST_SYNC_TABLE_TNAME = "table_name";
        final static String LAST_SYNC_TABLE_DATE = "sync_date";

        static public void create(SQLiteDatabase db) {
            db.execSQL("create table " + LAST_SYNC_TABLE + " (" +
                    LAST_SYNC_TABLE_TNAME + " TEXT PRIMARY KEY," +
                    LAST_SYNC_TABLE_DATE + " TEXT);" );
        }

        public static void drop(SQLiteDatabase db) {
            db.execSQL("drop table " + LAST_SYNC_TABLE + ";");
        }

        public static String getLastUpdate(SQLiteDatabase db, String tableName) {
            String[] args = {tableName};
            Cursor cursor = db.query(LAST_SYNC_TABLE, null, LAST_SYNC_TABLE_TNAME + " = ?",args , null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(LAST_SYNC_TABLE_DATE));
            }
            return null;
        }

        public static void setLastUpdate(SQLiteDatabase db, String table, String date) {
            ContentValues values = new ContentValues();
            values.put(LAST_SYNC_TABLE_TNAME, table);
            values.put(LAST_SYNC_TABLE_DATE, date);

            db.insertWithOnConflict(LAST_SYNC_TABLE,LAST_SYNC_TABLE_TNAME,values, SQLiteDatabase.CONFLICT_REPLACE);
        }
}
