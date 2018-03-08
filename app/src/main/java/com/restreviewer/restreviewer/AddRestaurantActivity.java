package com.restreviewer.restreviewer;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.restreviewer.restreviewer.Models.Model;
import com.restreviewer.restreviewer.Models.Restaurant;

import java.io.IOException;

public class AddRestaurantActivity extends AppCompatActivity {
    Uri imageUri;
    ImageView imageView;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        final EditText restName = (EditText) findViewById(R.id.editName);
        final EditText restAddress = (EditText) findViewById(R.id.editAddress);
        final EditText restFoodType = (EditText) findViewById(R.id.editFoodType);
        final EditText restPhone = (EditText) findViewById(R.id.editPhone);
        final CheckBox Deliveries = (CheckBox) findViewById(R.id.restaurant_deliveries);
        final CheckBox Kosher = (CheckBox) findViewById(R.id.restaurant_kosher);

        ImageButton galleryButton = (ImageButton) findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(openGallery , 0);
            }
        });

        imageView = (ImageView) findViewById(R.id.add_restaurant_image);

        Button addButton = (Button) findViewById(R.id.add_restaurant);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Restaurant newRest = new Restaurant();
                newRest.setName(restName.getText().toString());
                newRest.setAddress(restAddress.getText().toString());
                newRest.setType(restFoodType.getText().toString());
                newRest.setPhone(restPhone.getText().toString());
                newRest.setDeliveries(Deliveries.isChecked());
                newRest.setKosher(Kosher.isChecked());

                final ProgressDialog nDialog = new ProgressDialog(AddRestaurantActivity.this);
                nDialog.setMessage("Loading..");
                nDialog.setTitle("Loading");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();


                if (newRest.getName().length() == 0 || newRest.getAddress().length() == 0 || newRest.getPhone().length() == 0){
                    Toast.makeText(MyApplication.getContext(),"Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }



                Model.instance().addRestaurant(newRest, new Model.AddRestaurantListener(){
                    @Override
                    public void done(String key) {
                        newRest.setId(key);
                        System.out.println("restaurant successfully saved");
                        Model.instance().saveImage(key,imageBitmap, new OnSuccessListener<String>(){
                            @Override
                            public void onSuccess(String message) {
                                Toast.makeText(
                                        MyApplication.getContext(), "Restaurant saved! " + message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                                intent.putExtra("Restaurant", newRest);
                                startActivity(intent);
                                nDialog.dismiss();
                                finish();
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 0) {
                imageUri = data.getData();

                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(MyApplication.getContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
}
