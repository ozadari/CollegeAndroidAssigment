package com.restreviewer.restreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        final Spinner foodType = (Spinner) findViewById(R.id.inputFoodType);
        final String[] items = {"Indian", "Fast Food", "Toasts"};
        ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        foodType.setAdapter(adapter);

        final EditText restName = (EditText) findViewById(R.id.inputName);
        final EditText phoneNumber = (EditText) findViewById(R.id.inputPhone);
        Button addButton = (Button) findViewById(R.id.btnSendRestToServer);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurant newRest = new Restaurant();
                newRest.Name = restName.getText().toString();
                newRest.Telephone = phoneNumber.getText().toString();
                newRest.FoodType = foodType.getSelectedItem().toString();

                Model.instance().addRestaurant(newRest);
            }
        });
    }
}
