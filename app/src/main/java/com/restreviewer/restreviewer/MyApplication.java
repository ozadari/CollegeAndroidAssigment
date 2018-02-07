package com.restreviewer.restreviewer;

import android.app.Application;
import android.content.Context;

/**
 * Created by paz on 07/02/2018.
 */

public class MyApplication extends Application{
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
