package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class NewActivity extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserCarNumber,newUserCarModel;
    private Button save;
    private ImageView updateProfilePic;
    private StorageReference storageReference;
    String name,email,carNumber,carModel;
    int ImageBack=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        newUserName = findViewById(R.id.etNameUpdate);
        newUserEmail = findViewById(R.id.etEmailUpdate);
        newUserCarNumber = findViewById(R.id.etCarNumber);
        newUserCarModel = findViewById(R.id.etCarModel);
        save = findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.image5);

        storageReference = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("UserProfile").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Update");

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


    public void  choseImage(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,ImageBack);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ImageBack){
            if(resultCode==RESULT_OK){
                Uri ImageData=data.getData();

                final StorageReference Imagename= storageReference.child("image"+ImageData.getLastPathSegment());

                Imagename.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference imagestore=FirebaseDatabase.getInstance().getReference().child("Image").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("image", String.valueOf(uri));

                                imagestore.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //  Toast.makeText(RegisterActivity.this, "Finally Complete", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }
}
