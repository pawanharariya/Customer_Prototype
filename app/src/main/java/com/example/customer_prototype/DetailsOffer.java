package com.example.customer_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsOffer extends AppCompatActivity {

    ImageView offer;
    TextView genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_offer);

        offer = findViewById(R.id.offer);
        genre = findViewById(R.id.txtGenre);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Offer");

        Intent intent = getIntent();
        String genreoff = intent.getExtras().getString("Description");
        //  int image=intent.getExtras().getInt("Thumbnail");
        Picasso.get().load(intent.getStringExtra("Thumbnail")).into(offer);

        genre.setText(genreoff);

    }
}
