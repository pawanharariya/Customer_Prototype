package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TermAdapter extends RecyclerView.Adapter<TermViewHolder> {

    Context mContext;
    ArrayList<TermModel> termModelList;


   public TermAdapter(Context context,ArrayList<TermModel> termModelList ){
       this.mContext=context;
       this.termModelList=termModelList;
   }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.term_adapter,parent,false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
       TermModel termModel=termModelList.get(position);

       holder.desc.setText(termModel.getGenre());
       Picasso.get().load(termModel.getOffer()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,DetailsOffer.class);

                intent.putExtra("Description",termModelList.get(position).getGenre());
                intent.putExtra("Thumbnail",termModelList.get(position).getOffer());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return termModelList.size();
    }
}
