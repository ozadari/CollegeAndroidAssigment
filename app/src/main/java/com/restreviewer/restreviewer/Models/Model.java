package com.restreviewer.restreviewer.Models;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.restreviewer.restreviewer.DAL.LocalDB;
import com.restreviewer.restreviewer.DAL.LocalRestaurants;
import com.restreviewer.restreviewer.DAL.RemoteDB;
import com.restreviewer.restreviewer.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public interface LoadImageListener{
        void onResult(List<Restaurant> imageBmp);
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

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void loadImages(final List<Restaurant> restaurants, final LoadImageListener listener) {
        if (restaurants.size() == 0)
            listener.onResult(restaurants);

        final Counter counter = new Counter(restaurants.size());

        for (final Restaurant restaurant : restaurants) {
            final String imageName = restaurant.getId() + ".jpg";
            Uri uri = loadImageUriFromFile(imageName);
            if (uri != null){
                restaurant.setImageUri(uri);

                if (counter.Up())
                    listener.onResult(restaurants);
            }else{
                remote.loadImageByBytes(restaurant, new OnSuccessListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        if (bitmap != null){
                            saveImageToFile(bitmap, imageName);    //save the image locally for next time
                            bitmap.recycle();
                            bitmap = null;
                            restaurant.setImageUri(loadImageUriFromFile(imageName));
                        }

                        if (counter.Up())
                            listener.onResult(restaurants);
                    }
                });
            }
        }

        //listener.onResult(comments);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        OutputStream out = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
            out.close();

            //add the picture to the gallery so we dont need to manage the cache size
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            MyApplication.getContext().sendBroadcast(mediaScanIntent);
            Log.d("tag","add image to cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private Uri loadImageUriFromFile(String imageFileName){

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(dir,imageFileName);

        if (!imageFile.exists()){
            return null;
        }

        Uri uri = Uri.fromFile(imageFile);
        Log.d("tag","got image from cache: " + imageFileName);
        return uri;
    }

    private class Counter {
        public Integer count = 0;
        public Integer limit;


        public Counter(Integer limit){
            this.limit = limit;
        }

        public Boolean Up(){
            count++;
            return (count == limit || count == 5);
        }
    }
}
