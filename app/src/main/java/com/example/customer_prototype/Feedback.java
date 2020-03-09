package com.example.customer_prototype;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends Activity {
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    private EditText feedback,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        username= (EditText) findViewById(R.id.username);
        feedback= (EditText) findViewById(R.id.feedback);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        auth=FirebaseAuth.getInstance();


    }

    public void feedbacksent(View view) {


        ref.child(auth.getCurrentUser().getPhoneNumber()).child("id").setValue(auth.getCurrentUser().getUid());
        ref.child(auth.getCurrentUser().getPhoneNumber()).child("Username").setValue(username.getText().toString());
        ref.child(auth.getCurrentUser().getPhoneNumber()).child("feedback").setValue(feedback.getText().toString());
        finish();

    }
}
