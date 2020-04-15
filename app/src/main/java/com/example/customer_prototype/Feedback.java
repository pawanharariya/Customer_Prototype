package com.example.customer_prototype;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends Activity {
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    private EditText message, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        email = findViewById(R.id.email);
        message = findViewById(R.id.message);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        auth = FirebaseAuth.getInstance();

    }

    public void feedbacksent(View view) {


        ref.child("Feedback").child(auth.getCurrentUser().getUid()).child("phone").setValue(auth.getCurrentUser().getPhoneNumber());
        ref.child("Feedback").child(auth.getCurrentUser().getUid()).child("message").setValue(message.getText().toString());
        ref.child("Feedback").child(auth.getCurrentUser().getUid()).child("email").setValue(email.getText().toString());
        finish();

    }
}
