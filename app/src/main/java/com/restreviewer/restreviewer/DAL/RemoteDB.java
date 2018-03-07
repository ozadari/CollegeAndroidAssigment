package com.restreviewer.restreviewer.DAL;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.restreviewer.restreviewer.Models.Comment;
import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paz on 07/02/2018.
 * firebase class
 */

public class RemoteDB {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();
    DatabaseReference restaurantsReference = database.getReference("Restaurants");
    DatabaseReference commentsReference = database.getReference("Comments");

    public RemoteDB(Context context){
        Firebase.setAndroidContext(context);
    }

    public void addRestaurant(Restaurant restaurant, final Model.AddRestaurantListener listener) {
        final DatabaseReference newRestRef = restaurantsReference.push();
        newRestRef.setValue(restaurant, new Firebase.CompletionListener(){
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    listener.done(restaurantsReference.getKey());
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

    public void loadImageByBytes(final Restaurant restaurant, final OnSuccessListener<Bitmap> listener){
        storageRef.child("images/" + restaurant.getId() + ".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] data) {
                Bitmap bmp;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                listener.onSuccess(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                listener.onSuccess(null);
            }
        });
    }

    public void uploadImage(String restaurantId, Bitmap bitmap, final OnSuccessListener successListener){
        // Create a reference to 'images/mountains.jpg'
        StorageReference imageRef = storageRef.child("images/" + restaurantId + ".jpg");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageByteArray);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("image upload failed");
                successListener.onSuccess("without image");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                System.out.println("image successfully saved");
                successListener.onSuccess("");
            }
        });
    }
}
