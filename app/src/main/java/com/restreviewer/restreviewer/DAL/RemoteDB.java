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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Restaurants");
    final List<Restaurant> localRestaurants = new ArrayList<Restaurant>();

    public RemoteDB(Context context){
    }

    public void addRestaurant(Restaurant restaurant) {
        myRef.push().setValue(restaurant);
    }

    public void getRestaurants(final Model.GetRestaurantsListener listener) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                localRestaurants.clear();
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
