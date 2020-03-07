package com.example.customer_prototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MyAccountUser extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserCarNumber,newUserCarModel;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    String name,email,carNumber,carModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        newUserName = findViewById(R.id.etNameUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        newUserCarNumber = findViewById(R.id.etCarNumber);
        newUserCarModel = findViewById(R.id.etCarModel);
        save = findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        save = findViewById(R.id.btnSave);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
 databaseReference = firebaseDatabase.getReference().child("UserProfile");

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

                name=newUserName.getText().toString();
                email=newUserEmail.getText().toString();
                carNumber=newUserCarNumber.getText().toString();
                carModel=newUserCarModel.getText().toString();


                if(name.isEmpty() || email.isEmpty()|| carNumber.isEmpty() ||carModel.isEmpty()){
                    newUserName.setError("enter name");
                    newUserEmail.setError("enter email");
                    newUserCarNumber.setError("enter carNumber");
                    newUserCarModel.setError("enter carModel");
                }
                else {
                    databaseReference.child("name").setValue(name);
                    databaseReference.child(("email")).setValue(email);
                    databaseReference.child("carNumber").setValue(carNumber);
                    databaseReference.child("carModel").setValue(carModel);


                    StorageReference imageReference = storageReference.child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                   imageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MyAccountUser.this, "Imagge upload", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            }
                        });
            }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data!=null &&data.getData() != null){
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
