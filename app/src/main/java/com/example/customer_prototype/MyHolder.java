package com.example.customer_prototype;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //ImageView mImageView;
    //TextView mTitle,mDes;
    ItemClickListner itemClickListner;

    TextView latitude,longitude,name,shopname,email,genre;

    MyHolder(@NonNull View itemView) {
        super(itemView);

        this.latitude=itemView.findViewById(R.id.latitude);
        this.longitude=itemView.findViewById(R.id.longitude);
        this.name=itemView.findViewById(R.id.name);
        this.shopname=itemView.findViewById(R.id.shopname);
        this.email=itemView.findViewById(R.id.email);
        this.genre=itemView.findViewById(R.id.genre);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        this.itemClickListner.onItemClickListner(view,getLayoutPosition());
    }

    public void setItemClickListner(ItemClickListner ic){
        this.itemClickListner=ic;
    }
}