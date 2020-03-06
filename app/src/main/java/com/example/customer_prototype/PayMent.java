package com.example.customer_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PayMent extends AppCompatActivity implements PaymentResultListener {

    EditText et_Amount;
    Button payment;
    float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);


        et_Amount=findViewById(R.id.enterAmount);
        payment=findViewById(R.id.btnPay);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_Amount.getText().toString().isEmpty()){
                    Toast.makeText(PayMent.this, "Enter Payment", Toast.LENGTH_SHORT).show();
                }
                else {
                    amount = Integer.parseInt(et_Amount.getText().toString().trim());
                    amount = amount * 100;

                    startPayment(amount);
                }
            }
        });
    }
    private void startPayment(float amount) {

        Checkout checkout=new Checkout();
        //set Logo
        //  checkout.setImage(R.drawable.ic_launcher_background);
        ////reference current object
        final Activity activity=this;
        try{
            JSONObject options=new JSONObject();
            options.put("name","Anvay Technosolution Pvt. Ltd.");
            options.put("description","BnYnMHMUCcHvy5");
            options.put("currency","INR");
            options.put("amount",amount);
            checkout.open(activity,options);
        }
        catch(Exception e){
            Log.d("Error",e.toString());
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "payment successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show();
    }
}
