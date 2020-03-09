package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class RequestForTicket extends AppCompatActivity {

    Spinner spinner;
    Button btnSubmit;
    EditText etDetails;

    FirebaseAuth firebaseAuth;


    FirebaseDatabase database;
    DatabaseReference reference;

    TextView selected;
    Members member;

    int userId=10000;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_ticket);


        firebaseAuth=FirebaseAuth.getInstance();
        spinner=findViewById(R.id.spinner);
        etDetails=findViewById(R.id.etDetails);
        btnSubmit=findViewById(R.id.submit);
        member=new Members();

        reference=database.getInstance().getReference().child("Help And Support");

        List<String> list=new ArrayList<>();
        list.add(0,"Select Issue");
        list.add("Payment Deducted But Order Not Confirmed");
        list.add("Complaint Regarding Product Quaility");
        list.add("Discount Coupon Not Working");
        list.add("Where Is my order");
        list.add("Installation Process");

        ArrayAdapter<String> arrayAdapter;

        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Issue"))
                {

                }
                else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    id=(int) dataSnapshot.getChildrenCount();
                    userId=userId+id;

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details=etDetails.getText().toString();
                //member.setDetails(details);
                // member.setIssue(spinner.getSelectedItem().toString());
                String key=reference.push().getKey();
                String s="open";
                reference.child(firebaseAuth.getCurrentUser().getUid()).child("MC"+String.valueOf(userId)).child("Details").setValue(details);
                reference.child(firebaseAuth.getCurrentUser().getUid()).child("MC"+String.valueOf(userId)).child("Issue").setValue(spinner.getSelectedItem().toString());
                reference.child(firebaseAuth.getCurrentUser().getUid()).child("MC"+String.valueOf(userId)).child("status").setValue(s);
                //reference.child("MC"+String.valueOf(userId)).child("Details").setValue(details);
                //reference.child("MC"+String.valueOf(userId)).child("Issue").setValue(spinner.getSelectedItem().toString());
                //reference.child("MC"+String.valueOf(userId)).child("UserID").setValue(firebaseAuth.getCurrentUser().getUid());
                Toast.makeText(RequestForTicket.this, "value Stored Succesfully", Toast.LENGTH_SHORT).show();
                //reference.child("MC"+String.valueOf(userId)).setValue(member);
                etDetails.setText("");
                spinner.setSelection(0);
            }
        });
    }
}
