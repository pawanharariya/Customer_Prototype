package com.example.customer_prototype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity implements PaymentResultListener {

    ImageView imageScanner;
    private long amount, currentAmount = 0;
    private Button addMoney, proceed;
    private EditText amountText;
    private View addAmountDialog;
    private String userId;
    private TextView userBalanceTextView;
    private ProgressDialog post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loadUI();
        SharedPreferences sharedPreferences = getSharedPreferences("app",MODE_PRIVATE);
        userId = sharedPreferences.getString("userId",null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user_balance").document(userId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(Dashboard.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                    Log.e("error",e.toString());
                    return;
                }
                if (documentSnapshot.exists())
                    currentAmount = (long) documentSnapshot.get("userBalance");
                else
                    Log.e("amount", "document doesn't exist");
                userBalanceTextView.setText("\u20B9 " + currentAmount);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.dashboard1:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }

                return false;
            }
        });

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAmountDialog.setVisibility(View.VISIBLE);
            }
        });
        imageScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ScannerActivity.class));
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(amountText.getText().toString()) || userId==null)
                    Toast.makeText(Dashboard.this, "Unknown Error", Toast.LENGTH_SHORT).show();
                else {
                    amount = Integer.parseInt(amountText.getText().toString().trim());
                    if (amount <= 0)
                        Toast.makeText(Dashboard.this, "Please enter valid amount", Toast.LENGTH_SHORT).show();
                    else
                        startPayment(amount*100);
                }
            }
        });
    }

    public void startPayment(long amountToAdd) {
        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_sVrW5vtI3Nd2pZ\t");
//        checkout.setImage(R.drawable.pay);
        final Activity activity = Dashboard.this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Anvay Technosolution Pvt. Ltd.");
//            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amountToAdd);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("error", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        addAmountDialog.setVisibility(View.GONE);
        Log.e("Payment Success", s);
        Checkout.clearUserData(this);
        postData(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("payment error", s);
        if (i == Checkout.NETWORK_ERROR) {
            Log.e("error payment failed", "Checkout.NETWORK_ERROR");
            Toast.makeText(this, "Poor Network, Payment Failed", Toast.LENGTH_SHORT).show();
        }
        if (i == Checkout.INVALID_OPTIONS) {
            Log.e("error payment failed", "Checkout.INVALID_OPTIONS");
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();

        }
        if (i == Checkout.PAYMENT_CANCELED) {
            Log.e("error payment failed", "Checkout.PAYMENT_CANCELED");
            Toast.makeText(this, "Payment Canceled by user", Toast.LENGTH_SHORT).show();

        }
        if (i == Checkout.TLS_ERROR) {
            Log.e("error payment failed", "Checkout.TLS_ERROR");
            Toast.makeText(this, "Payment Not supported", Toast.LENGTH_SHORT).show();
        }
    }

    private void postData(final String s) {
        post = new ProgressDialog(this);
        post.setCancelable(false);
        post.setMessage("Adding amount");
        post.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user_balance").document(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userBalance", currentAmount + amount);
        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        postTransaction(s);
                    }
                });
    }

    public void backDialog(View view) {
        addAmountDialog.setVisibility(View.GONE);
    }

    private void loadUI() {
        addMoney = findViewById(R.id.add_money);
        amountText = findViewById(R.id.amount);
        proceed = findViewById(R.id.proceed);
        addAmountDialog = findViewById(R.id.add_amount_dialog);
        userBalanceTextView = findViewById(R.id.user_balance);
        imageScanner = findViewById(R.id.imageScanner);
    }
    @ServerTimestamp Date time;
    private void postTransaction(String s) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("wallet_transactions").document();
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("amount", amount);
        data.put("transactionId", s);
        data.put("timestamp", FieldValue.serverTimestamp());
        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Dashboard.this, "Money added to your wallet", Toast.LENGTH_SHORT).show();
                        post.dismiss();
                    }
                });
    }
}
