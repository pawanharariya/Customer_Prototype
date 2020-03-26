package com.example.customer_prototype;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Order_DataViewHolder extends RecyclerView.ViewHolder {
    public TextView shop,date,orderid,status;
    public CardView cardView;
    public LinearLayout linearLayout;
    public CircleImageView profile;

    public Order_DataViewHolder(@NonNull View itemView) {
        super(itemView);
        shop = itemView.findViewById(R.id.shop_id);
        date = itemView.findViewById(R.id.date_id);
        orderid = itemView.findViewById(R.id.order_id);
        status = itemView.findViewById(R.id.status_id);
        cardView = itemView.findViewById(R.id.card_view);
        linearLayout = itemView.findViewById(R.id.linear_lay);
        profile = itemView.findViewById(R.id.profile_image);

    }
}
