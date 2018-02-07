package com.restreviewer.restreviewer.Models;

import com.restreviewer.restreviewer.DAL.LocalDB;
import com.restreviewer.restreviewer.DAL.RemoteDB;
import com.restreviewer.restreviewer.MyApplication;

/**
 * Created by paz on 07/02/2018.
 */

public class Model {
    private final static Model instance = new Model();

    LocalDB local;
    RemoteDB remote;

    private Model() {
        local = new LocalDB(MyApplication.getContext());
        remote = new RemoteDB(MyApplication.getContext());
    }

    public static Model instance() {
        return instance;
    }

    public interface AddRestaurantListener {
        void done(String key);
    }

    public void addRestaurant(Restaurant newRest, AddRestaurantListener listener) {
        remote.addRestaurant(newRest, listener);
    }
}
