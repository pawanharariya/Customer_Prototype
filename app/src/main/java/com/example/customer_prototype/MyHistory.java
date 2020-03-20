package com.example.customer_prototype;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyHistory extends RecyclerView.ViewHolder {

    ItemClickListner itemClickListner;

    TextView detailsHistory,issueHistory,statusHistorty,id;


    public MyHistory(@NonNull View itemView) {
        super(itemView);

        this.detailsHistory=itemView.findViewById(R.id.details);
        this.issueHistory=itemView.findViewById(R.id.issue);
        this.statusHistorty=itemView.findViewById(R.id.status);
        this.id=itemView.findViewById(R.id.id);

       // itemView.setOnClickListener(this);
    }

   /* @Override
    public void onClick(View view) {
        this.itemClickListner.onItemClickListner(view,getLayoutPosition());
    }*/
}
