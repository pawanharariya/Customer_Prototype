package com.example.customer_prototype;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;
import java.util.SplittableRandom;

public class Order_Display_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Order_DataSetFirebase> arrayList;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Order_DataSetFirebase> options;
    private FirebaseRecyclerAdapter<Order_DataSetFirebase, Order_DataViewHolder> firebaseRecyclerAdapter;
    //initialize these variable

    Button btnActive,btnCancel,btnHistory;

    final int[] img = {
            R.drawable.back,
            R.drawable.back1,
            R.drawable.back2,
            R.drawable.back3,
            R.drawable.back4,
            R.drawable.back5,
            R.drawable.back6,
            R.drawable.back7,
            R.drawable.back8,
            R.drawable.back9
    };



    @Override
    protected void onStart() {
        firebaseRecyclerAdapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        firebaseRecyclerAdapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_display);
        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Order_DataSetFirebase>();
        databaseReference = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference().child("orders");
        databaseReference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Order_DataSetFirebase>().setQuery(databaseReference, Order_DataSetFirebase.class).build();


        btnActive=findViewById(R.id.btn_active_order);
        btnCancel=findViewById(R.id.btn_cancel_order);
        btnHistory=findViewById(R.id.btn_history_order);

        btnCancel.setOnClickListener(new Click());
        btnActive.setOnClickListener(new Click());
        btnHistory.setOnClickListener(new Click());

            card();
    }
    public class Click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btn_active_order:
                   // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    databaseReference = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference().child("active");
                    databaseReference.keepSynced(true);
                    options = new FirebaseRecyclerOptions.Builder<Order_DataSetFirebase>().setQuery(databaseReference, Order_DataSetFirebase.class).build();
                    card();
                    firebaseRecyclerAdapter.startListening();
                    break;

                case R.id.btn_cancel_order:
                    databaseReference = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference().child("cancel");
                    databaseReference.keepSynced(true);
                    options = new FirebaseRecyclerOptions.Builder<Order_DataSetFirebase>().setQuery(databaseReference, Order_DataSetFirebase.class).build();
                    card();
                    firebaseRecyclerAdapter.startListening();
                    break;

                case R.id.btn_history_order:
                    databaseReference = FirebaseDatabase.getInstance("https://customerprototype-29375.firebaseio.com/").getReference().child("history");
                    databaseReference.keepSynced(true);
                    options = new FirebaseRecyclerOptions.Builder<Order_DataSetFirebase>().setQuery(databaseReference, Order_DataSetFirebase.class).build();
                    card();
                    firebaseRecyclerAdapter.startListening();break;
                default:

            }
        }
    }

    void card(){
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Order_DataSetFirebase, Order_DataViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Order_DataViewHolder holder, int position, @NonNull final Order_DataSetFirebase model) {
                holder.linearLayout.setBackgroundResource(img[new Random().nextInt(img.length)]);
                //FirebaseRecyclerView main task where it fetching data from model
                holder.shop.setText("Shop : "+model.getShop());
                holder.date.setText("Date : "+model.getDate());
                holder.status.setText("Status : "+model.getStatus());
                holder.orderid.setText("ID : "+model.getOrderid());
                Picasso.get().load(model.getProfile()).into(holder.profile);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Order_Display_Activity.this, Order_Information_Activity.class);
                        intent.putExtra("shop",model.getShop());
                        intent.putExtra("date",model.getDate());
                        intent.putExtra("status",model.getStatus());
                        intent.putExtra("orderid",model.getOrderid());
                        intent.putExtra("profile",model.getProfile());


                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public Order_DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Order_DataViewHolder(LayoutInflater.from(Order_Display_Activity.this).inflate(R.layout.order_grid,viewGroup,false));
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
