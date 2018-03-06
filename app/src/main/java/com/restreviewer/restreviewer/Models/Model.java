package com.restreviewer.restreviewer.Models;

import com.restreviewer.restreviewer.DAL.LocalDB;
import com.restreviewer.restreviewer.DAL.LocalRestaurants;
import com.restreviewer.restreviewer.DAL.RemoteDB;
import com.restreviewer.restreviewer.MyApplication;
import com.restreviewer.restreviewer.Restaurant;

import java.util.List;

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

    public interface AddCommentListener {
        void done(String key);
    }

    public interface GetRestaurantsListener {
        void done(List<Restaurant> stList);
    }

    public interface GetCommentsListener {
        void done(List<Comment> stList);
    }

    public void addRestaurant(Restaurant newRest, AddRestaurantListener listener) {
        remote.addRestaurant(newRest, listener);
    }

    public void getComments(Integer restaurantId, GetCommentsListener listener) {
        remote.getComments(restaurantId, listener);
    }

    public void getRestaurants(final GetRestaurantsListener listener) {
        final String lastUpdateDate = LocalRestaurants.getLastUpdateDate(local.getReadbleDB());

        remote.getRestaurants(new GetRestaurantsListener() {
            @Override
            public void done(List<Restaurant> restaurants) {
                if(restaurants != null && restaurants.size() > 0) {
                    //update the local DB
                    String recentUpdate = lastUpdateDate;
                    LocalRestaurants.truncate(local.getWritableDB());
                    for (Restaurant res : restaurants) {
                        LocalRestaurants.addRestaurant(local.getWritableDB(), res);
                        if (recentUpdate == null || res.getLastUpdated().compareTo(recentUpdate) > 0) {
                            recentUpdate = res.getLastUpdated();
                        }
                    }
                    LocalRestaurants.setLastUpdateDate(local.getWritableDB(), recentUpdate);
                }
                //return the complete student list to the caller
                List<Restaurant> res = LocalRestaurants.getAllRestaurants(local.getReadbleDB());
                listener.done(res);
            }
        },lastUpdateDate);
    }

    public void addComment(Comment comment, AddCommentListener listener) {
        remote.addComment(comment, listener);
    }
}
