package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;

    FirebaseAuth firebaseAuth;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        firebaseAuth=FirebaseAuth.getInstance();


        listView=findViewById(R.id.listview);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    // String email = ds.child("email").getValue(String.class);
                    //String name = ds.child("name").getValue(String.class);
                    //Log.d("TAG", email + " / " + name);


                    String value=ds.getValue().toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }

                /* if(dataSnapshot!=null){
                    String value=dataSnapshot.getValue().toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }*/

                //Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                //Log.d("sagar","Value is: " + map);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
