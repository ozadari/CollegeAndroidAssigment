package com.restreviewer.restreviewer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Get the restaurant
        final Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("Restaurant");

        // Initialize all the text by the rest info
        TextView restaurantAddressText = (TextView) findViewById(R.id.restaurant_address);
        restaurantAddressText.setText(restaurant.getAddress());
        TextView restaurantTypeText = (TextView) findViewById(R.id.restaurant_type);
        restaurantTypeText.setText(restaurant.getType());
        TextView restaurantPhoneText = (TextView) findViewById(R.id.restaurant_phone);
        restaurantPhoneText.setText(restaurant.getPhone());
        CheckBox restaurantParkingBox = (CheckBox) findViewById(R.id.restaurant_deliveries);
        restaurantParkingBox.setClickable(false);
        restaurantParkingBox.setEnabled(false);
        restaurantParkingBox.setChecked(restaurant.getDeliveries());
        CheckBox restaurantKosherBox = (CheckBox) findViewById(R.id.restaurant_kosher);
        restaurantKosherBox.setClickable(false);
        restaurantKosherBox.setEnabled(false);
        restaurantKosherBox.setChecked(restaurant.getKosher());

        // Manage the comments manager
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new CommentsListFragment();

        // Comments fragment
        Bundle bundle = new Bundle();
        bundle.putString("restaurantId", restaurant.getId().toString());
        fragmentTransaction.replace(R.id.comments_frag_container, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();

        // Make a call if press on the call
        ImageButton callButton = (ImageButton) findViewById(R.id.call_button);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + ((Restaurant)getIntent().getSerializableExtra("Restaurant")).getPhone()));
                startActivity(intent);
            }
        });

        Button addReview = (Button) findViewById(R.id.add_review);

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddCommentActivity.class);
                intent.putExtra("Restaurant", restaurant);
                startActivity(intent);
            }
        });
    }
}
