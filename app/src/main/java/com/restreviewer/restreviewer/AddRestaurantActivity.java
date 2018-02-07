package com.restreviewer.restreviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
    }

    public void SaveRestaurant(View view) {
        Restaurant newRest = new Restaurant();
        Model.instance().addRestaurant(newRest, new Model.AddRestaurantListener() {
            @Override
            public void done(String key) {
                System.out.println("comment successfully saved");
                /* Save image
                Model.instance().saveImage(key,imageBitmap, new OnSuccessListener<String>(){
                    @Override
                    public void onSuccess(String message) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                MyApplication.getContext(), "Comment saved! " + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                        intent.putExtra("Restaurant", restaurant);
                        startActivity(intent);
                        finish();
                    }
                });*/
            }
        });
    }
}
