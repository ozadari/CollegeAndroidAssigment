package com.restreviewer.restreviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.restreviewer.restreviewer.Models.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Restaurant> localRestaurants = new ArrayList<Restaurant>();
    ResurantListAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Restaurants");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                for (DataSnapshot restSnapshot: dataSnapshot.getChildren()) {
                    Restaurant rest = restSnapshot.getValue(Restaurant.class);
                    localRestaurants.add(rest);
                }
                ListView restList = (ListView)findViewById(R.id.resturant_main_list);
                restAdapter = new ResurantListAdapter();
                restList.setAdapter(restAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ThisISTag", "Failed to read value.", error.toException());
            }
        });

        // TODO: Get All Restaurant

        // TODO: click on rest event

        // Add Restaurant button
        Button addRest = (Button) findViewById(R.id.btnAddRest);

        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddRestaurantActivity.class);
                startActivity(intent);
            }
        });
    }

    class ResurantListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localRestaurants.size();
        }

        @Override
        public Object getItem(int position) {
            return localRestaurants.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.restaurant_list_item,null);
            }

            ImageView image = (ImageView) convertView.findViewById(R.id.restaurant_image);
            TextView name = (TextView) convertView.findViewById(R.id.restName);
            TextView type = (TextView) convertView.findViewById(R.id.restType);
            TextView phone = (TextView) convertView.findViewById(R.id.restPhone);

            convertView.setTag(position);

            Restaurant currRest = localRestaurants.get(position);
            name.setText(currRest.Name);
            type.setText(currRest.FoodType);
            phone.setText(currRest.Telephone);
            // TODO: set the restaurant image

            return convertView;
        }
    }
}
