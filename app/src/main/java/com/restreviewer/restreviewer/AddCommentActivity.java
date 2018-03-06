package com.restreviewer.restreviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.restreviewer.restreviewer.Models.Comment;
import com.restreviewer.restreviewer.Models.Model;

public class AddCommentActivity extends AppCompatActivity {
    EditText editName;
    EditText editText;
    RatingBar ratingBar;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restaurant = (Restaurant) getIntent().getSerializableExtra("Restaurant");
        getSupportActionBar().setTitle(restaurant.getName());
        editName = (EditText) findViewById(R.id.editTitle);
        editText = (EditText) findViewById(R.id.editText);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

    }

    public void saveComment(View view){
        Comment comment = new Comment(editName.getText().toString(), editText.getText().toString(), ratingBar.getRating(), restaurant.getId());

        final ProgressBar progressbar = new ProgressBar(AddCommentActivity.this);
        progressbar.setIndeterminate(true);
        progressbar.setVisibility(View.VISIBLE);

        Model.instance().addComment(comment, new Model.AddCommentListener() {
            @Override
            public void done(String key) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(
                        MyApplication.getContext(), "Comment saved! ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), RestaurantActivity.class);
                intent.putExtra("Restaurant", restaurant);
                startActivity(intent);
                finish();
            }
        });
    }
}
