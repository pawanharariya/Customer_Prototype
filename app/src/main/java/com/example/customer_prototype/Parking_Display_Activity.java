package com.example.customer_prototype;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Parking_Display_Activity extends AppCompatActivity {
    Button btnActive,btnCancel,btnHistory;
    private RecyclerView recyclerView;
    private Parking_DataViewHolder adapter;
    private List<Parking_DataSetFirebase> parkingDataSetFirebaseList;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_display);

        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parkingDataSetFirebaseList = new ArrayList<>();
        adapter = new Parking_DataViewHolder(this, parkingDataSetFirebaseList);
        recyclerView.setAdapter(adapter);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Book Parking");
        
        //1. SELECT * FROM Artists
        dbArtists = FirebaseDatabase.getInstance("https://customerprototype-29375-fbcfa.firebaseio.com/").getReference();
        Query que = FirebaseDatabase.getInstance("https://customerprototype-29375-fbcfa.firebaseio.com/").getReference();
        que.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            parkingDataSetFirebaseList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Parking_DataSetFirebase parkingDataSetFirebase = snapshot.getValue(Parking_DataSetFirebase.class);
                    parkingDataSetFirebaseList.add(parkingDataSetFirebase);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public class Click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                default:

            }
        }
    }
}
