package com.example.customer_prototype;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class Order_Information_Activity extends Activity {

    TextView shop_id,date_id,status_id,order_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_infromation);

        String shop = getIntent().getStringExtra("shop");
        String date = getIntent().getStringExtra("date");
        String status = getIntent().getStringExtra("status");
        String order = getIntent().getStringExtra("orderid");

        shop_id = findViewById(R.id.shop_id);
        order_id = findViewById(R.id.order_id);
        status_id = findViewById(R.id.status_id);
        date_id = findViewById(R.id.date_id);

        shop_id.setText("Shop : "+shop);
        date_id.setText("Date : "+date);
        status_id.setText("Status : "+status);
        order_id.setText("Order ID : "+order);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount=0.7f;
        params.y = -20;

        getWindow().setAttributes(params);


    }
}

