package com.example.customer_prototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Parking_Information_Activity extends Activity {

    TextView shop_id,name_id,genre_id,email_id;
    Button book_park;
    CircleImageView profile_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_infromation);

        shop_id = findViewById(R.id.shop_id);
        name_id = findViewById(R.id.name_id);
        email_id = findViewById(R.id.email_id);
        genre_id = findViewById(R.id.genre_id);
        profile_id = findViewById(R.id.imageView);
        book_park = findViewById(R.id.book_park);
        book_park.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(Parking_Information_Activity.this, "Booked Successfully!", Toast.LENGTH_SHORT).show();
            }
        });


        String shop = getIntent().getStringExtra("shop");
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String genre = getIntent().getStringExtra("genre");
        String image = getIntent().getStringExtra("image");

        shop_id.setText("Shop : "+shop);
        name_id.setText("Name : "+name);
        genre_id.setText("Genre : "+genre);
        email_id.setText("Email : "+email);
        Picasso.get().load(image).into(profile_id);





        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount=0.7f;
        params.y = -20;

        getWindow().setAttributes(params);


    }
}

