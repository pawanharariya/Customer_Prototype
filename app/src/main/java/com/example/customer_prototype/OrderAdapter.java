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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    ArrayList<Order> al;
    public OrderAdapter(Context context, ArrayList<Order>al){
        this.context = context;
        this.al = al;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order,null);
        return new OrderAdapter.OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order=al.get(position);
        holder.image.setImageResource(order.imageids);
        holder.name.setText(order.name);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public OrderViewHolder(@NonNull View v) {
            super(v);
            image= v.findViewById(R.id.imgfab);
            name=v.findViewById(R.id.line);
        }
    }
}
