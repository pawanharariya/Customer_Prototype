package com.example.customer_prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Foodmenu extends RecyclerView.Adapter<Foodmenu.FoodViewHolder> {
    Context context;
    ArrayList<Food> al;

    public Foodmenu(Context context, ArrayList<Food> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.foodmenu, null);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = al.get(position);
        holder.image.setImageResource(food.imageid);
        holder.name.setText(food.food_name);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public FoodViewHolder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.foodimg);
            name = v.findViewById(R.id.foodname);
        }
    }
}
