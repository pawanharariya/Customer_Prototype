package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Model> models;//this array list create a list of which parameters define in our model class

    //now create a parameterized constructor


    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);//this line inflate our row
        return new MyHolder(view);//this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
          holder.mTitle.setText(models.get(position).getTitle());
          holder.mDes.setText(models.get(position).getDescription());
          holder.mImageView.setImageResource(models.get(position).getImg());//here we used image resource
          //because we will use images in our resource folder which is drawable


        //this method is than you can use when you want to use one activity

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {

                String gTitle=models.get(position).getTitle();//these object get our data from previos activity
                String gDesc=models.get(position).getDescription();//same upper
                BitmapDrawable bitmapDrawable=(BitmapDrawable)holder.mImageView.getDrawable();//this will get our image from drawable


                Bitmap bitmap=bitmapDrawable.getBitmap();

                ByteArrayOutputStream stream=new ByteArrayOutputStream();//image will get steam and byte

                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);//it will compress our image
                byte[] bytes = stream.toByteArray();

                //get our data with intent

                Intent intent=new Intent(c,AnotherActivity.class);
                intent.putExtra("iTitle",gTitle);
                intent.putExtra("iDesc",gDesc);
                intent.putExtra("iImage",bytes);

                c.startActivity(intent);
            }
        });

        //if you want to use different activites than you can use this logic
      /*  holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                if(models.get(position).getTitle().equals("Clothes Shop")){
                    //then you can move anotther from if body
                }
                if(models.get(position).getTitle().equals("Jewelly")){
                    //then you can move anotther from if body
                }
                if(models.get(position).getTitle().equals("Shop")){
                    //then you can move anotther from if body
                }
                if(models.get(position).getTitle().equals("shops")){
                    //then you can move anotther from if body
                }
                if(models.get(position).getTitle().equals("mall")){
                    //then you can move anotther from if body
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
