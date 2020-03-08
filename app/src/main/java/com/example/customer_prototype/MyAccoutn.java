package com.example.customer_prototype;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;


public class MyAccoutn extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserCarNumber,newUserCarModel;
    private Button save;
    private Button back;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    String name,email,carNumber,carModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_accoutn);

        newUserName = findViewById(R.id.etNameUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        newUserCarNumber = findViewById(R.id.etCarNumber);
        newUserCarModel = findViewById(R.id.etCarModel);
        save = findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserProfile").child(firebaseAuth.getCurrentUser().getUid());


        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = newUserName.getText().toString();
                email = newUserEmail.getText().toString();
                carNumber = newUserCarNumber.getText().toString();
                carModel = newUserCarModel.getText().toString();
                if (name.isEmpty() || email.isEmpty() || carNumber.isEmpty() || carModel.isEmpty()) {
                    newUserName.setError("enter name");
                    newUserEmail.setError("enter email");
                    newUserCarNumber.setError("enter carNumber");
                    newUserCarModel.setError("enter carModel");
                } else {
                    databaseReference.child("name").setValue(name);
                    databaseReference.child(("email")).setValue(email);
                    databaseReference.child("carNumber").setValue(carNumber);
                    databaseReference.child("carModel").setValue(carModel);
                }
            }
        });

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   try {
                       String name = dataSnapshot.child("name").getValue().toString();
                       String email = dataSnapshot.child("email").getValue().toString();
                       String carNumber = dataSnapshot.child("carNumber").getValue().toString();
                       String carModel = dataSnapshot.child("carModel").getValue().toString();
                       newUserName.setText(name);
                       newUserEmail.setText(email);
                       newUserCarNumber.setText(carNumber);
                       newUserCarModel.setText(carModel);
                   }catch (Exception e){
                       Log.d("MyAccountn","exception"+e);
                   }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
