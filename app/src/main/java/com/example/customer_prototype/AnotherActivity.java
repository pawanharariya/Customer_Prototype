package com.example.customer_prototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AnotherActivity extends AppCompatActivity {

    TextView mTitleTv,mDescTv;
    ImageView mImageIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        //in this activity we will use a back button

        ActionBar actionBar=getSupportActionBar();

        mTitleTv=findViewById(R.id.title);
        mDescTv=findViewById(R.id.description);
        mImageIv=findViewById(R.id.imageView1);

        //now get our data from intent in which we put our data

        Intent intent=getIntent();

        String mTitle=intent.getStringExtra("iTitle");
        String mDesc=intent.getStringExtra("iDesc");

        //now decode image because from previous activity we get our image in bytes
        byte[] mBytes=getIntent().getByteArrayExtra("iImage");

        Bitmap bitmap= BitmapFactory.decodeByteArray(mBytes,0,mBytes.length);

      //  actionBar.setTitle(mTitle);//which title we get previous activity that will set in our action bar

        //now set our data in our view wgich we get in our previous activity

        mTitleTv.setText(mTitle);
        mDescTv.setText(mDesc);
        mImageIv.setImageBitmap(bitmap);


        }
}
