package com.restreviewer.restreviewer;

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

        /*ImageButton cameraButton = (ImageButton) findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });*/

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

                Model.instance().addRestaurant(newRest, new Model.AddRestaurantListener(){
                    @Override
                    public void done(String key) {
                        System.out.println("restaurant successfully saved");
                        Model.instance().saveImage(key,imageBitmap, new OnSuccessListener<String>(){
                            @Override
                            public void onSuccess(String message) {
                                Toast.makeText(
                                        MyApplication.getContext(), "Comment saved! " + message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                                intent.putExtra("Restaurant", newRest);
                                startActivity(intent);
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
                Uri imageUri = data.getData();

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
