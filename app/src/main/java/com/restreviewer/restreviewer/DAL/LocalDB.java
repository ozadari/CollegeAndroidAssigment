package com.restreviewer.restreviewer.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paz on 07/02/2018.
 */

public class LocalDB {
    // The local DB Version
    final static int VERSION = 1;

    // The helper initialize the db every time we load the app
    Helper sqlDb;

    public LocalDB(Context context){
        sqlDb = new Helper(context);
    }

    public SQLiteDatabase getWritableDB(){
        return sqlDb.getWritableDatabase();
    }

    public SQLiteDatabase getReadbleDB(){
        return sqlDb.getReadableDatabase();
    }

    class Helper extends SQLiteOpenHelper {
        public Helper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            LocalRestaurants.create(db);
            LastSyncLocal.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LocalRestaurants.drop(db);
            LastSyncLocal.drop(db);
            onCreate(db);
        }
    }
}
