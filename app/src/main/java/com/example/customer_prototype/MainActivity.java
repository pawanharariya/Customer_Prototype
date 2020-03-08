package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import technolifestyle.com.imageslider.FlipperLayout;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    Button btnNearstFood,btnNearstHospital,btnNearstParking;

    GoogleMap mGoogleMap;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View viewHeader;



    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private Button mLogoutBtn;

    public static final int DEFAULT_ZOOM = 15;
    private final double TMU_LAT = 28.825583;
    private final double TMU_LNG = 78.657610;

    Marker mm;


    private boolean mLocationPermissionGranted;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private final int PLAY_SERVICES_ERROR_CODE = 9002;

    private FusedLocationProviderClient mLocationClient;

    DrawerLayout mNavDrawer;
    ViewFlipper viewFlipper;
    private RecyclerView recyclerView,recyclerView1;
    private ArrayList<MainModel> mainModels;
    ArrayList<Order> al1;
    private MainAdapter mainAdapter;
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>adapter1;

    TextView textView;
    Location location;

    TextView txtName,txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //location st karne ka code on toolbar


        btnNearstFood=findViewById(R.id.btnFood);
        btnNearstHospital=findViewById(R.id.btnHospital);
        btnNearstParking=findViewById(R.id.btnParing);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                String city = hereLocation(location.getLatitude(), location.getLongitude());
                getSupportActionBar().setTitle(city);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "not Found permission", Toast.LENGTH_SHORT).show();
            }
        }
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case  R.id.dashboard1:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case  R.id.home:
                        return true;

                    case  R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }

                return false;
            }
        });

        btnNearstFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FreeParkingMap.class));
            }
        });

        btnNearstParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FreeParkingMap.class));
            }
        });

        btnNearstHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FreeParkingMap.class));
            }
        });




       /* BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_home()).commit();
        }*/

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        // fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        //fetchLocation();

        Toolbar Drawertoolbar=findViewById(R.id.toolbar);
        setSupportActionBar(Drawertoolbar);

        mNavDrawer=findViewById(R.id.drawer_layout);

        //assign variable
        viewFlipper=findViewById(R.id.vflipper);
        //   recyclerView=findViewById(R.id.recycle_view);
        recyclerView1=findViewById(R.id.recycle_view_1);

        //create array
        int logo[]={R.drawable.img1,R.drawable.img2,R.drawable.img3
                ,R.drawable.img4,R.drawable.img5};

        for(int images: logo)
        {
            flipperImages(images);
        }


        mainModels=new ArrayList<>();
        for(int i=0;i<logo.length;i++){
            MainModel model=new MainModel(logo[i]);
            mainModels.add(model);
        }
        Order oo1=new Order(R.drawable.great,"Great Offer");
        Order oo2=new Order(R.drawable.combo,"Combo");
        Order oo3=new Order(R.drawable.newarr,"New Arrival");
        Order oo4=new Order(R.drawable.healthyf,"Healthy Food");
        Order oo5=new Order(R.drawable.fab1,"Food");
        Order oo6=new Order(R.drawable.fab1,"Dinner");
        al1 = new ArrayList<>();
        al1.add(oo1);
        al1.add(oo2);
        al1.add(oo3);
        al1.add(oo4);
        al1.add(oo5);
        al1.add(oo6);
        // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this
        //   ,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity.this
                ,LinearLayoutManager.HORIZONTAL,false);

        //    recyclerView.setLayoutManager(linearLayoutManager);
        //  recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        mainAdapter=new MainAdapter(getApplicationContext(),mainModels);
        adapter1=new OrderAdapter(this,al1);
        //  recyclerView.setAdapter(mainAdapter);
        recyclerView1.setAdapter(adapter1);
        //navigation bar ka code
        NavigationView navigationView=findViewById(R.id.navigation_view);


        viewHeader=navigationView.getHeaderView(0);

        mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("UserProfile").child(mCurrentUser.getUid());

        txtEmail=viewHeader.findViewById(R.id.headeremail);
        txtName=viewHeader.findViewById(R.id.headername);

        try {
               databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        txtName.setText(name);
                        txtEmail.setText(email);
                    }catch(Exception e){

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (NullPointerException e){
            Log.d("datanull","data"+e);
        }



        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mNavDrawer,Drawertoolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        //add toggle button on toolbar
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

    }

    public void flipperImages(int image)
    {

        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

  /*  private void fetchLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;

                    SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragnment);
                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }*/

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        LatLng latlng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions().position(latlng).title("I Am Here");
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,15));
        mGoogleMap.addMarker(markerOptions);
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLocation();
                }
                break;
        }
    }*/

    @Override
    public void onBackPressed() {
        if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_my_account:
            {
                Toast.makeText(this, "jump", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MyAccoutn.class));
                break;
            }
            case R.id.nav_help_and_support:
            {
                startActivity(new Intent(MainActivity.this,HelpAndSupport.class));

                break;
            }
            case R.id.nav_orders:
            {
                startActivity(new Intent(MainActivity.this,OrdersActivity.class));

                break;
            }
            case R.id.nav_sign_out:
            {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

                break;
            }
            case R.id.nav_payment:
            {
                // mAuth.signOut();
                startActivity(new Intent(MainActivity.this,PayMent.class));

                break;
            }
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
       if(mCurrentUser == null){
            sendUserToLogin();

        }
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

   /* private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            return true;
                        case R.id.nav_favorites:
                            selectedFragment = new fragment_favourite();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new fragment_search();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            String city = hereLocation(location.getLatitude(), location.getLongitude());
                            textView.setText(city);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"not Found", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }


                }else{
                    Toast.makeText(this,"Permission not granted !",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private String hereLocation(double lat, double lon){
        String cityname = "";
        Geocoder geocoder =  new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(lat,lon,10);
            if(addresses.size()>0){
                for (Address adr: addresses){
                    if (adr.getAddressLine(0) != null && adr.getAddressLine(0).length() >0 ){
                        cityname=adr.getAddressLine(0);
                        break;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return cityname;
    }


}

