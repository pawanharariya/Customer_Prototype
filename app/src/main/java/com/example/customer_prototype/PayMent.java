package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PayMent extends AppCompatActivity implements PaymentResultListener {

    EditText et_Amount;
    Button payment;
    private CodeScanner codeScanner;
    private CodeScannerView scannerView;
    TextView resultData;

    float amount;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);



        scannerView=findViewById(R.id.scanner_view);
        codeScanner=new CodeScanner(this,scannerView);
        resultData=findViewById(R.id.resultQr);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });


        et_Amount=findViewById(R.id.enterAmount);
        payment=findViewById(R.id.btnPay);
        back=findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        codeScanner.startPreview();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(PayMent.this, "Camera permision is required", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}
