package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TermAndCondition extends AppCompatActivity {

    private DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    ArrayList<TermModel> termModelArrayList;
    RecyclerView recyclerView;

    TermAdapter termAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        recyclerView=findViewById(R.id.Recyclerview);
        termModelArrayList=new ArrayList<>();

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        firebaseDatabase=FirebaseDatabase.getInstance("https://customerprototype-29375-fbcfa.firebaseio.com/");
        databaseReference=firebaseDatabase.getReference();

        storageReference= FirebaseStorage.getInstance().getReference();



       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                  try {
                      String key = dataSnapshot1.getKey();
                      TermModel termModel = dataSnapshot1.getValue(TermModel.class);
                      Log.d("key", "key" + key);
                      Log.d("url", "url" + dataSnapshot.child(key).getValue().toString());
                      termModelArrayList.add(termModel);
                  }catch (Exception e){

                  }
                  }

                  termAdapter = new TermAdapter(TermAndCondition.this, termModelArrayList);
                  recyclerView.setLayoutManager(new GridLayoutManager(TermAndCondition.this, 2));
                  recyclerView.setAdapter(termAdapter);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }
}
