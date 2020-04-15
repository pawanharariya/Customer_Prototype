package com.example.customer_prototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(2000);
                Intent in = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(in);
                finish();
            } catch (Exception e) {

            }
        }
    }
}
