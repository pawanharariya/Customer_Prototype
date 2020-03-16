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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    ArrayList<Order> al;

    public UserAdapter(Context context, ArrayList<Order>al){
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_activity,null);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Order order=al.get(position);
        holder.image.setImageResource(order.imageid);
        holder.name.setText(order.itemname);
        holder.prize.setText(""+order.amount);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,prize;
        public UserViewHolder(@NonNull View v) {
            super(v);
            image= v.findViewById(R.id.pro);
            name=v.findViewById(R.id.itemname);
            prize=v.findViewById(R.id.itemprize);
        }
    }
}
