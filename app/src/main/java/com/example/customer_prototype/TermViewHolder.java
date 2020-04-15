package com.example.customer_prototype;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class TermViewHolder extends RecyclerView.ViewHolder {
    TextView desc;
    ImageView imageView;
    CardView cardView;

    TermViewHolder(@NonNull View itemView) {
        super(itemView);

        this.desc = itemView.findViewById(R.id.book_title_id);
        this.imageView = itemView.findViewById(R.id.book_img_id);
        cardView = itemView.findViewById(R.id.cardview_id);


    }
}
