package com.example.customer_prototype;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class PayMent extends AppCompatActivity {

    EditText et_Amount;
    long amount;
    Button back;
    Button pay;
    private CodeScanner codeScanner;
    private CodeScannerView scannerView;
    private String partnerId, customerId;
    private ProgressDialog post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        customerId = sharedPreferences.getString("userId", null);
        et_Amount = findViewById(R.id.enterAmount);
        back = findViewById(R.id.back_btn);
        scannerView = findViewById(R.id.scanner_view);
        pay = findViewById(R.id.pay);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        partnerId = result.getText();
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Amount.getText().toString().isEmpty() || customerId == null || partnerId == null) {
                    Toast.makeText(PayMent.this, "Enter amount", Toast.LENGTH_SHORT).show();
                } else {
                    amount = Long.parseLong(et_Amount.getText().toString().trim());
                    Log.e("buttonClicked","payment initiated");
                    startTransfer(amount);
                }
            }
        });
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
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        codeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(PayMent.this, "Camera permission is required", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void startTransfer(long amount) {
        post = new ProgressDialog(this);
        post.setCancelable(false);
        post.setMessage("Processing Please Wait...");
        post.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user_balance").document(customerId);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("error", "DocumentSnapshot data: " + document.getData());
                                long userCurrentBalance = (long) document.get("userBalance");
                                updateUserBalance(userCurrentBalance, amount);
                            } else {
                                Log.d("error", "user doesn't exist");
                            }
                        } else {
                            Log.d("error", "Unknown Error", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PayMent.this, "Unknown Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserBalance(long currentBalance, long amount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user_balance").document(customerId);
        Map<String, Object> data = new HashMap<>();
        data.put("userBalance", currentBalance - amount);
        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getPartnerBalance(partnerId, amount);
                        }

                    }
                });
    }

    private void getPartnerBalance(String partnerId, long amount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("partner_balance").document(partnerId);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("error", "DocumentSnapshot data: " + document.getData());
                                long partnerCurrentBalance = (long) document.get("partnerBalance");
                                updatePartnerBalance(partnerCurrentBalance, amount);
                            } else {
                                Log.d("error", "Partner doesn't exist");
                            }
                        } else {
                            Log.d("error", "Unknown Error", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PayMent.this, "Unknown Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePartnerBalance(long currentBalance, long amount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("partner_balance").document(partnerId);
        Map<String, Object> data = new HashMap<>();
        data.put("partnerBalance", currentBalance + amount);
        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            postTransaction();
                        }
                    }
                });
    }

    private void postTransaction() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("purchase_transactions").document();
        Map<String, Object> data = new HashMap<>();
        data.put("customerId", customerId);
        data.put("partnerId", partnerId);
        data.put("amount", amount);
//        data.put("transactionId", s);
        data.put("timestamp", FieldValue.serverTimestamp());
        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PayMent.this, "Transfer successful", Toast.LENGTH_SHORT).show();
                        post.dismiss();
                        Intent homeIntent = new Intent(PayMent.this, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        finish();
                    }
                });
    }
}
