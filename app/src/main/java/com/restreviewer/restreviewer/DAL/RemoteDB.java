package com.restreviewer.restreviewer.DAL;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restreviewer.restreviewer.Models.Comment;
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
    DatabaseReference restaurantsReference = database.getReference("Restaurants");
    DatabaseReference commentsReference = database.getReference("Comments");

    public RemoteDB(Context context){
        Firebase.setAndroidContext(context);
    }

    public void addRestaurant(Restaurant restaurant, final Model.AddRestaurantListener listener) {
        final DatabaseReference newRestaurantRef = restaurantsReference.push();
        newRestaurantRef.setValue(restaurant, new Firebase.CompletionListener(){
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    listener.done(newRestaurantRef.getKey());
                }
            }
        });
    }

    public void getComments(Integer restaurantId, final Model.GetCommentsListener listener) {
        Query queryRef = commentsReference.orderByChild("restaurantId").equalTo(restaurantId);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Comment> allRestComments = new ArrayList<Comment>();

                if (snapshot.exists()) {

                    for (DataSnapshot child: snapshot.getChildren()) {
                        final Comment comment = child.getValue(Comment.class);

                        comment.setId(child.getKey());
                        allRestComments.add(comment);
                    }
                }
                else {
                    Log.e("no comments", "no comments");
                }

                listener.done(allRestComments);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThisISTag", "Failed to read value.", error.toException());
            }
        });
    }

    public void getRestaurants(final Model.GetRestaurantsListener listener, String lastUpdateDate) {
        Query queryRef = restaurantsReference.orderByChild("lastUpdated").startAt(lastUpdateDate);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                final List<Restaurant> allRestaurants = new ArrayList<Restaurant>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Restaurant restaurant = child.getValue(Restaurant.class);
                        allRestaurants.add(restaurant);
                    }
                }
                else {
                    Log.e("no restaurants", "no restaurants");
                }

                listener.done(allRestaurants);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThisISTag", "Failed to read value.", error.toException());
            }
        });
    }

    public void addComment(Comment comment, final Model.AddCommentListener listener) {
        final DatabaseReference newCommentRef = commentsReference.push();
        newCommentRef.setValue(comment, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    listener.done(newCommentRef.getKey());
                }
            }
        });
    }
}
