package com.example.customer_prototype;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    List<Orders> lstOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        lstOrder = new ArrayList<>();
        lstOrder.add(new Orders("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian));
        lstOrder.add(new Orders("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot));
        lstOrder.add(new Orders("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples));
        lstOrder.add(new Orders("The Martian", "Categorie Book", "Description book", R.drawable.themartian));
        lstOrder.add(new Orders("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith));
        lstOrder.add(new Orders("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian));
        lstOrder.add(new Orders("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot));
        lstOrder.add(new Orders("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples));
        lstOrder.add(new Orders("The Martian", "Categorie Book", "Description book", R.drawable.themartian));
        lstOrder.add(new Orders("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith));
        lstOrder.add(new Orders("The Vegitarian", "Categorie Book", "Description book", R.drawable.thevigitarian));
        lstOrder.add(new Orders("The Wild Robot", "Categorie Book", "Description book", R.drawable.thewildrobot));
        lstOrder.add(new Orders("Maria Semples", "Categorie Book", "Description book", R.drawable.mariasemples));
        lstOrder.add(new Orders("The Martian", "Categorie Book", "Description book", R.drawable.themartian));
        lstOrder.add(new Orders("He Died with...", "Categorie Book", "Description book", R.drawable.hediedwith));

        RecyclerView myrv = findViewById(R.id.recyclerview_id);

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstOrder);

        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);

    }
}
