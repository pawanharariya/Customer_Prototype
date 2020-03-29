package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class Parking_DataViewHolder extends RecyclerView.Adapter<Parking_DataViewHolder.ArtistViewHolder> {
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

    private Context mCtx;
    private List<Order_DataSetFirebase> orderDataSetFirebaseList;

    public Parking_DataViewHolder(Context mCtx, List<Order_DataSetFirebase> orderDataSetFirebaseList) {
        this.mCtx = mCtx;
        this.orderDataSetFirebaseList = orderDataSetFirebaseList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.parking_grid, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Order_DataSetFirebase orderDataSetFirebase = orderDataSetFirebaseList.get(position);
        holder.shop.setText("Shop : "+orderDataSetFirebase.shop);
        holder.date.setText("Date : " + orderDataSetFirebase.date);
        holder.status.setText("Status : " + orderDataSetFirebase.status);
        holder.orderid.setText("ID : " + orderDataSetFirebase.orderid);
        Picasso.get().load(orderDataSetFirebase.profile).into(holder.profile);
        holder.linearLayout.setBackgroundResource(img[new Random().nextInt(img.length)]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, Order_Information_Activity.class);
                intent.putExtra("shop",orderDataSetFirebase.shop);
                intent.putExtra("date",orderDataSetFirebase.date);
                intent.putExtra("status",orderDataSetFirebase.status);
                intent.putExtra("orderid",orderDataSetFirebase.orderid);
                intent.putExtra("profile",orderDataSetFirebase.profile);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataSetFirebaseList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView shop,date,orderid,status;
        public CardView cardView;
        public LinearLayout linearLayout;
        public CircleImageView profile;

        public ArtistViewHolder(@NonNull View itemView) {
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
}
