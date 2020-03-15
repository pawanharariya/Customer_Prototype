package com.example.customer_prototype;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FetchActivity extends Activity {


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private EditText etname,etemail,etshop,etlongitude,etlatitude,etgenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);

        etname=findViewById(R.id.fetch_name);
        etemail=findViewById(R.id.fetch_email);
        etgenre=findViewById(R.id.fetch_genre);
        etshop=findViewById(R.id.fetch_shop);
        etlatitude=findViewById(R.id.fetch_lati);
        etlongitude=findViewById(R.id.fetch_long);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://customerprototype-29375-fbcfa.firebaseio.com/");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String latitude = dataSnapshot.child("+917425563249").child("Latitude").getValue().toString();
                    String longitude = dataSnapshot.child("+917425563249").child("Longitude").getValue().toString();
                    String name = dataSnapshot.child("+917425563249").child("Name").getValue().toString();
                    String shopname = dataSnapshot.child("+917425563249").child("ShopName").getValue().toString();
                    String email = dataSnapshot.child("+917425563249").child("email").getValue().toString();
                    String genre = dataSnapshot.child("+917425563249").child("genre").getValue().toString();

                    etname.setText(name);
                    etemail.setText(email);
                    etshop.setText(shopname);
                    etgenre.setText(genre);
                    etlatitude.setText(latitude);
                    etlongitude.setText(longitude);



                }catch (Exception e){
                    Log.d("FetchActivity","exception"+e);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

}
