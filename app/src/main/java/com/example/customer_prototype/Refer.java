package com.example.customer_prototype;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Refer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Refer");
        Button know_more = findViewById(R.id.knowmore_btn);
        know_more.setOnClickListener(new Click());
    }

    public class Click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.knowmore_btn:
                    Intent intent = new Intent(Refer.this,Refer_Info.class);
                    startActivity(intent);
                    break;
                default:

            }
        }
    }
}
