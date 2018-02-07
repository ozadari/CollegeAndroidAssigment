package com.restreviewer.restreviewer.DAL;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;
import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paz on 07/02/2018.
 * firebase class
 */

public class RemoteDB {

    public RemoteDB(Context context){
        Firebase.setAndroidContext(context);
    }

    public void addRestaurant(Restaurant restaurant, final Model.AddRestaurantListener listener) {
    /*    Firebase ref = new Firebase("https://restreviewer-d0351.firebaseio.com/restaurants");

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
        });*/
    }

    public void getRestaurants(final Model.GetRestaurantsListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Restaurants");

        final List<Restaurant> localRestaurants = new ArrayList<Restaurant>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot restSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = restSnapshot.getValue(Restaurant.class);
                    localRestaurants.add(rest);
                }

                listener.done(localRestaurants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThisISTag", "Failed to read value.", error.toException());
            }
        });
    }
}
