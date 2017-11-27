package com.restreviewer.restreviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.restreviewer.restreviewer.Models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Restaurant> data;
    ResurantListAdapter restAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Get All Restaurant
        data = new ArrayList<Restaurant>();
        data.add(new Restaurant());

        ListView restList = (ListView)findViewById(R.id.resturant_main_list);
        restAdapter = new ResurantListAdapter();
        restList.setAdapter(restAdapter);

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
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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

            return convertView;
        }
    }
}
