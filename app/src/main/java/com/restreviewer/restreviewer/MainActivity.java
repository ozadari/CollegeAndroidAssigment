package com.restreviewer.restreviewer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ResurantListAdapter restAdapter;
    List<Restaurant> restaurants;
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 112);

        }

        getIntent().getExtras().get("favorites");

        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Loading");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        Model.instance().getRestaurants(new Model.GetRestaurantsListener() {
            @Override
            public void done(final List<Restaurant> allRest) {
                Model.instance().loadImages(allRest, new Model.LoadImageListener(){

                    @Override
                    public void onResult(List<Restaurant> restaurantsWithImages) {
                        restaurants = restaurantsWithImages;
                        ListView restList = (ListView)findViewById(R.id.restaurant_main_list);
                        restAdapter = new ResurantListAdapter();
                        restList.setAdapter(restAdapter);
                        nDialog.dismiss();
                    }
                });

                /*restaurants = allRest;
                ListView restList = (ListView)findViewById(R.id.restaurant_main_list);
                restAdapter = new ResurantListAdapter();
                restList.setAdapter(restAdapter);*/
                };
        });

        // Add Restaurant button
        FloatingActionButton addRest = (FloatingActionButton) findViewById(R.id.btnAddRest);

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
            name.setText(currRest.getName());
            type.setText(currRest.getType());
            phone.setText(currRest.getPhone());
            Picasso.with(MyApplication.getContext()).load(currRest.getImageUri()).resize(300,200).into(image);
            Button btnWatch = (Button) convertView.findViewById(R.id.btnWatchRest);
            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currRest.emptyImage();
                    Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                    intent.putExtra("Restaurant", currRest);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
