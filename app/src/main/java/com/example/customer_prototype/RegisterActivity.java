package com.example.customer_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText name, email, CarModel, CarColor, CarNumber;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        CarModel = findViewById(R.id.etCarModel);
        CarNumber = findViewById(R.id.etCarNumber);
        CarColor = findViewById(R.id.etColor);
        saveBtn = findViewById(R.id.btnRegister);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || CarColor.getText().toString().isEmpty()
                        || CarModel.getText().toString().isEmpty() || CarNumber.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docRef = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name.getText().toString());
                user.put("Email", email.getText().toString());
                user.put("CarModel", CarModel.getText().toString());
                user.put("CarNumber", CarNumber.getText().toString());
                user.put("CarColor", CarColor.getText().toString());


                //add user to database
                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: User Profile Created." + userID);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to Create User " + e.toString());
                    }
                });
            }
        });
    }
}
