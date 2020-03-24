package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<ShowDataOnMap> showDataOnMaps;//this array list create a list of which parameters define in our model class

    //now create a parameterized constructor


    public MyAdapter(Context c, ArrayList<ShowDataOnMap> showDataOnMaps) {
        this.c = c;
        this.showDataOnMaps = showDataOnMaps;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);//this line inflate our row
        return new MyHolder(view);//this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.latitude.setText(String.valueOf(showDataOnMaps.get(position).getLatitude()));
        holder.longitude.setText(String.valueOf(showDataOnMaps.get(position).getLongitude()));
        holder.name.setText(showDataOnMaps.get(position).getName());
        holder.shopname.setText(showDataOnMaps.get(position).getShopName());
        holder.email.setText(showDataOnMaps.get(position).getEmail());
        holder.genre.setText(showDataOnMaps.get(position).getGenre());
        // holder.mImageView.setImageResource(models.get(position).getImg());//here we used image resource
          //because we will use images in our resource folder which is drawable


        //this method is than you can use when you want to use one activity

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {

                String latitudeUser=String.valueOf(showDataOnMaps.get(position).getLatitude());//these object get our data from previos activity
                String longitudeUser=String.valueOf(showDataOnMaps.get(position).getLongitude());//same upper
                String  nameUser=showDataOnMaps.get(position).getName();
                String  shopNameUser=showDataOnMaps.get(position).getShopName();
                String  emailUser=showDataOnMaps.get(position).getEmail();
                String  genreUser=showDataOnMaps.get(position).getGenre();

                /* Log.d("latitude","lat"+latitudeUser);
                Log.d("longitude","lag"+longitudeUser);*/

                //BitmapDrawable bitmapDrawable=(BitmapDrawable)holder.mImageView.getDrawable();//this will get our image from drawable


                //Bitmap bitmap=bitmapDrawable.getBitmap();

                //ByteArrayOutputStream stream=new ByteArrayOutputStream();//image will get steam and byte

                //bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);//it will compress our image
                //byte[] bytes = stream.toByteArray();

                //get our data with intent

                Intent intent=new Intent(c,AnotherActivity.class);
                intent.putExtra("latitude",latitudeUser);
                intent.putExtra("longitude",longitudeUser);
                intent.putExtra("name",nameUser);
                intent.putExtra("shopname",shopNameUser);
                intent.putExtra("email",emailUser);
                intent.putExtra("genre",genreUser);

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
        return showDataOnMaps.size();
    }
}
