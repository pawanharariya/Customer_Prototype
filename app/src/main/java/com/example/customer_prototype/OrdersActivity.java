package com.example.customer_prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
    ArrayList<Order> al;
    RecyclerView rv;
    RecyclerView.Adapter<UserAdapter.UserViewHolder>adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Order o1=new Order(R.drawable.cccd,30,"Coffee");
        Order o2=new Order(R.drawable.ss,90,"Food");
        Order o3=new Order(R.drawable.s1,120,"Dinner");
        Order o4=new Order(R.drawable.cccd,30,"Coffee");
        Order o5=new Order(R.drawable.ss,90,"Food");
        Order o6=new Order(R.drawable.s1,120,"Dinner");
        al = new ArrayList<>();
        al.add(o1);
        al.add(o2);
        al.add(o3);
        al.add(o4);
        al.add(o5);
        al.add(o6);
        rv = findViewById(R.id.recycle_order);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new UserAdapter(this,al);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}
