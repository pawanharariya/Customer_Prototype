package com.example.customer_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Refer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Refer");

        Button know_more = findViewById(R.id.knowmore_btn);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        know_more.setOnClickListener(new Click());
        linearLayout.setOnClickListener(new Click());

    }

    public void share() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.putExtra(Intent.EXTRA_TEXT, "Play store Link has to be provide here. Thank You!");
        share_intent.setType("text/plain");
        startActivity(Intent.createChooser(share_intent, "share via"));
    }

    public class Click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.knowmore_btn:
                    Intent intent = new Intent(Refer.this, Refer_Info.class);
                    startActivity(intent);
                    break;
                case R.id.linearLayout:
                    share();
                    break;

                default:

            }
        }
    }
}
