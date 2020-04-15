package com.example.customer_prototype;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;

    FirebaseAuth firebaseAuth;
    FirebaseUser userID;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ds1;
    String user, hist;

    RecyclerView recycrView;
    List<String> arrayList = new ArrayList<>();
    ArrayList<HistoryModel> histMod = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, histMod);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        firebaseAuth = FirebaseAuth.getInstance();

        recycrView = findViewById(R.id.recy);
        recycrView.setHasFixedSize(true);
        recycrView.setLayoutManager(new LinearLayoutManager((this)));


        // listView=findViewById(R.id.listview);

        //arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        // listView.setAdapter(arrayAdapter);

        userID = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Help And Support").child(firebaseAuth.getCurrentUser().getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user = ds.getKey();

                            String details = dataSnapshot.child(user).child("Details").getValue().toString();
                            String issue = dataSnapshot.child(user).child("Issue").getValue().toString();
                            String status = dataSnapshot.child(user).child("status").getValue().toString();
                            histMod.add(new HistoryModel(details, issue, status, user));
                            //histMod.add(user);
                            recycrView.setAdapter(historyAdapter);

                            //  arrayAdapter.notifyDataSetChanged();
                            //  Log.d("details","details="+details);
                            //Log.d("issue","issue="+issue);
                            //Log.d("status","status="+status);
                         /*   ds1=firebaseDatabase.getInstance().getReference().child("Help And Support").child(firebaseAuth.getCurrentUser().getUid()).child(user);
                         ds1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                            if (dataSnapshots.exists())
                            {
                                for (DataSnapshot ds1 : dataSnapshots.getChildren())
                                {
                                    String us=ds1.getKey();
                                    Log.d("jobs","jobs"+us);

                                    // String value=ds.getValue().toString();
                                    String details = dataSnapshot.child(user).child("Details").getValue().toString();
                                    Log.d("user","user"+details);
                                    //String issue = dataSnapshot.child("Issue").getValue().toString();
                                    //String status = dataSnapshot.child("status").getValue().toString();


                                   // histMod.add(new HistoryModel(details, issue, status));
                                    //historyAdapter = new HistoryAdapter(HistoryActivity.this, histMod);
                                    //recycrView.setAdapter(historyAdapter);
                                    //hist=ds1.getKey();
                                    //Log.d("hist","hist"+hist);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/

                            // String value=ds.getValue().toString();
                            //arrayList.add(value);
                            //arrayAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        Log.d("error", "error" + e);
                        //  Toast.makeText(HistoryActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HistoryActivity.this, "not data", Toast.LENGTH_SHORT).show();
                }
                // historyAdapter =new MyAdapter(HistoryActivity.this,arrayList);
                //listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
