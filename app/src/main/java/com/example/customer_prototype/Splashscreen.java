package com.example.customer_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifImageView;

public class Splashscreen extends AppCompatActivity {

    GifImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new MyThread().start();


        new MyThread().start();
    }

    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try {
                Thread.sleep(2000);
                Intent in=new Intent(Splashscreen.this, MainActivity.class);
                startActivity(in);
                finish();
            }
            catch (Exception e)
            {

            }
        }
    }
}
