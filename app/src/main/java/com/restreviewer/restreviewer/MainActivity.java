package com.restreviewer.restreviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ResurantListAdapter restAdapter;
    List<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model.instance().getRestaurants(new Model.GetRestaurantsListener() {
            @Override
            public void done(final List<Restaurant> allRest) {
                restaurants = allRest;
                ListView restList = (ListView)findViewById(R.id.resturant_main_list);
                restAdapter = new ResurantListAdapter();
                restList.setAdapter(restAdapter);
                };
        });

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
            return restaurants.size();
        }

        @Override
        public Object getItem(int position) {
            return restaurants.get(position);
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

            final Restaurant currRest = restaurants.get(position);
            name.setText(currRest.Name);
            type.setText(currRest.FoodType);
            phone.setText(currRest.Telephone);

            Button btnWatch = (Button) convertView.findViewById(R.id.btnWatchRest);
            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                    intent.putExtra("id", currRest.Id);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
