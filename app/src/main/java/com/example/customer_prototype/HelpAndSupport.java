package com.example.customer_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpAndSupport extends AppCompatActivity {

    Button btnHistory,btnRequest;
    TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        btnHistory=findViewById(R.id.btnHistory);
        btnRequest=findViewById(R.id.btnRequest);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpAndSupport.this,HistoryActivity.class));
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpAndSupport.this,RequestForTicket.class));
            }
        });





    }
}
