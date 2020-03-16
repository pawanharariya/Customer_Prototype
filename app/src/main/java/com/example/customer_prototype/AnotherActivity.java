package com.example.customer_prototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AnotherActivity extends AppCompatActivity implements OnMapReadyCallback {

    //TextView mTitleTv,mDescTv;
    //ImageView mImageIv;

    TextView latitude,longitude,name,shopName,email,genre;
    GoogleMap mMap;
    String mLatitude;
    String mLongitude;
    String mNamme;
    String mShopName;
    String mGenre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        //in this activity we will use a back button

        ActionBar actionBar=getSupportActionBar();


        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map1);

        mapFragment.getMapAsync(this::onMapReady);

         latitude=findViewById(R.id.latitude);
         longitude=findViewById(R.id.longitude);
         name=findViewById(R.id.name);
         shopName=findViewById(R.id.shopname);
         email=findViewById(R.id.email);
         genre=findViewById(R.id.genre);

        /* now get our data from intent in which we put our data */

        Intent intent=getIntent();

        mLatitude=intent.getStringExtra("latitude");
        mLongitude=intent.getStringExtra("longitude");

        Log.d("mlatitude","lat"+mLatitude);
        Log.d("mlongitude","lag"+mLongitude);
        mNamme=intent.getStringExtra("name");
        mShopName=intent.getStringExtra("shopname");

        String mEmail=intent.getStringExtra("email");
        mGenre=intent.getStringExtra("genre");

        //now decode image because from previous activity we get our image in bytes
       // byte[] mBytes=getIntent().getByteArrayExtra("iImage");

        //Bitmap bitmap= BitmapFactory.decodeByteArray(mBytes,0,mBytes.length);

        //try {
          //  actionBar.setTitle(mTitle);//which title we get previous activity that will set in our action bar
        //}
        //catch(NullPointerException n){
          //  Log.d("dikkat", "onCreate: "+n);
       // }
        //now set our data in our view wgich we get in our previous activity

        latitude.setText(mLatitude);
        longitude.setText(mLongitude);
        name.setText(mNamme);
        shopName.setText(mShopName);
        email.setText(mEmail);
        genre.setText(mGenre);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
           mMap=googleMap;
           Double lat=Double.valueOf(mLatitude);
           Double lag=Double.valueOf(mLongitude);
           LatLng latLng=new LatLng(lat,lag);
           mMap.addMarker(new MarkerOptions().position(latLng).title(mNamme+"::"+mShopName+"::"+mGenre));
          CameraUpdate cameraUpdateFactory=CameraUpdateFactory.newLatLngZoom(latLng,16);
          mMap.moveCamera(cameraUpdateFactory);
           // mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
          // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
