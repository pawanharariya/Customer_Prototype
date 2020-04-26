package com.example.customer_prototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Parking_DataViewHolder extends RecyclerView.Adapter<Parking_DataViewHolder.ArtistViewHolder> {
    private Context mCtx;
    private List<Parking_DataSetFirebase> parkingDataSetFirebaseList;

    public Parking_DataViewHolder(Context mCtx, List<Parking_DataSetFirebase> parkingDataSetFirebaseList) {
        this.mCtx = mCtx;
        this.parkingDataSetFirebaseList = parkingDataSetFirebaseList;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.parking_grid, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Parking_DataSetFirebase parkingDataSetFirebase = parkingDataSetFirebaseList.get(position);
        holder.shop.setText("Shop Name : " + parkingDataSetFirebase.ShopName);
        holder.email.setText("Email : " + parkingDataSetFirebase.email);
        holder.genre.setText("Genre : " + parkingDataSetFirebase.genre);
        holder.name.setText("Name : " + parkingDataSetFirebase.Name);
        Picasso.get().load(parkingDataSetFirebase.Image).into(holder.profile);
        holder.linearLayout.setBackgroundResource(R.drawable.book_parking_card);
        holder.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences("app",Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId",null);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef =  db.collection("bookings").document();
                Map<String,Object> data = new HashMap<>();
                data.put("userId",userId);
                data.put("time", FieldValue.serverTimestamp());
                docRef.set(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(mCtx, "Booked Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, Parking_Information_Activity.class);
                intent.putExtra("shop", parkingDataSetFirebase.ShopName);
                intent.putExtra("name", parkingDataSetFirebase.Name);
                intent.putExtra("email", parkingDataSetFirebase.email);
                intent.putExtra("genre", parkingDataSetFirebase.genre);
                intent.putExtra("image", parkingDataSetFirebase.Image);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingDataSetFirebaseList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView genre, shop, name, email;
        public CardView cardView;
        public LinearLayout linearLayout;
        public CircleImageView profile;
        public Button bookNow;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            shop = itemView.findViewById(R.id.shop_id);
            name = itemView.findViewById(R.id.name_id);
            email = itemView.findViewById(R.id.email_id);
            genre = itemView.findViewById(R.id.genre_id);
            cardView = itemView.findViewById(R.id.card_view);
            linearLayout = itemView.findViewById(R.id.linear_lay);
            profile = itemView.findViewById(R.id.profile_image);
            bookNow = itemView.findViewById(R.id.book_now);
        }
    }
}
