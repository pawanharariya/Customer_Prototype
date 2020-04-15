package com.example.customer_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Order_Activity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvcategory;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        tvtitle = findViewById(R.id.txttitle);
        tvdescription = findViewById(R.id.txtDesc);
        tvcategory = findViewById(R.id.txtCat);
        img = findViewById(R.id.bookthumbnail);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        img.setImageResource(image);

    }
}
