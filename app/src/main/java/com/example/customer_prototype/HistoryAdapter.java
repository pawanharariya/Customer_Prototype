package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<MyHistory>  {

    Context c;
    ArrayList<HistoryModel> history;

    public HistoryAdapter(Context c, ArrayList<HistoryModel> historyModels) {
        this.c = c;
        this.history = historyModels;
    }


    @NonNull
    @Override
    public MyHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history,null);//this line inflate our row
        return new MyHistory(view);//this will return our view to holder classreturn null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistory holder, int position) {

        holder.detailsHistory.setText(String.valueOf(history.get(position).getDetails()));
        holder.issueHistory.setText(String.valueOf(history.get(position).getIssue()));
        holder.statusHistorty.setText(history.get(position).getStatus());
        holder.id.setText(history.get(position).getId());
      /*  holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {

                String  detailsHis=history.get(position).getDetails();
                String  issueHis=history.get(position).getIssue();
                String  statusHis=history.get(position).getStatus();

                //BitmapDrawable bitmapDrawable=(BitmapDrawable)holder.mImageView.getDrawable();//this will get our image from drawable


                //Bitmap bitmap=bitmapDrawable.getBitmap();

                //ByteArrayOutputStream stream=new ByteArrayOutputStream();//image will get steam and byte

                //bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);//it will compress our image
                //byte[] bytes = stream.toByteArray();

                //get our data with intent

                Intent intent=new Intent(c,HistoryShow.class);

                intent.putExtra("details",detailsHis);
                intent.putExtra("issue",issueHis);
                intent.putExtra("status",statusHis);

                c.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return history.size();
    }
}
