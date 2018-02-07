package com.restreviewer.restreviewer.DAL;
import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;
import android.content.Context;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by paz on 07/02/2018.
 * firebase class
 */

public class RemoteDB {

    public RemoteDB(Context context){
        Firebase.setAndroidContext(context);
    }

    public void addRestaurant(Restaurant restaurant, final Model.AddRestaurantListener listener) {
        Firebase ref = new Firebase("https://restreviewer-d0351.firebaseio.com/restaurants");

        final Firebase newRestRef = ref.push();
        newRestRef.setValue(restaurant, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    listener.done(newRestRef.getKey());
                }
            }
        });
    }

    /*public void addRestaurant(Restaurant restaurant){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("restaurants").child(restaurant.getId().toString());
        // TODO: understand how to save
        //Map<String, Object> value = new HashMap<>();
        //value.put("Id",restaurant.getId().toString());
        //value.put("name",restaurant.getName());
        //value.put("phone",restaurant.getPhone());
        //myRef.setValue(value);

        myRef.setValue(restaurant);
    }*/

}
