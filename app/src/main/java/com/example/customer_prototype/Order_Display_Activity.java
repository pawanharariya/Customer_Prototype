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

public class Order_Display_Activity extends AppCompatActivity {
    Button btnActive, btnCancel, btnHistory;
    DatabaseReference dbArtists;
    private RecyclerView recyclerView;
    private Order_DataViewHolder adapter;
    private List<Order_DataSetFirebase> orderDataSetFirebaseList;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            orderDataSetFirebaseList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order_DataSetFirebase orderDataSetFirebase = snapshot.getValue(Order_DataSetFirebase.class);
                    orderDataSetFirebaseList.add(orderDataSetFirebase);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_display);

        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDataSetFirebaseList = new ArrayList<>();
        adapter = new Order_DataViewHolder(this, orderDataSetFirebaseList);
        recyclerView.setAdapter(adapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Orders");

        btnActive = findViewById(R.id.btn_active_order);
        btnCancel = findViewById(R.id.btn_cancel_order);
        btnHistory = findViewById(R.id.btn_history_order);

        btnCancel.setOnClickListener(new Click());
        btnActive.setOnClickListener(new Click());
        btnHistory.setOnClickListener(new Click());

        //1. SELECT * FROM Artists
        dbArtists = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference().child("orders");
        Query que = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference()
                .child("orders");
        que.addListenerForSingleValueEvent(valueEventListener);
    }

    public class Click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_active_order:
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    Query active = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference()
                            .child("orders")
                            .orderByChild("status")
                            .equalTo("active");
                    active.addListenerForSingleValueEvent(valueEventListener);
                    break;

                case R.id.btn_cancel_order:
                    Query cancel = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference()
                            .child("orders")
                            .orderByChild("status")
                            .equalTo("cancel");
                    cancel.addListenerForSingleValueEvent(valueEventListener);
                    break;

                case R.id.btn_history_order:
                    Query history = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference()
                            .child("orders")
                            .orderByChild("status")
                            .equalTo("history");
                    history.addListenerForSingleValueEvent(valueEventListener);
                default:

            }
        }
    }
}
