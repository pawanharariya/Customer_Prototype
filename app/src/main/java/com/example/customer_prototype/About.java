package com.example.customer_prototype;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class About extends AppCompatActivity implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        , LocationListener {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    GoogleMap mGoogleMap;
//    ImageButton school=findViewById(R.id.school_nearby);

    ImageButton school,hospital,restraunt;


    Button btnNearstFood,btnNearstHospital,btnNearstParking;

    private boolean mLocationPermissionGranted;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private final int PLAY_SERVICES_ERROR_CODE = 9002;

    private FusedLocationProviderClient mLocationClient;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayoutBSheet;
    private ToggleButton tbUpDown;
    private ListView listView;
    private TextView txtCantante, txtCancion;
    private ContentLoadingProgressBar progbar;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code=99;
    double latitude,longitude;
    private int ProxymityRadius=1000;

    Button btnsort;

    SharedPreferences preferences;

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btnsort=findViewById(R.id.btnSort);

        school=findViewById(R.id.school_nearby);
        hospital=findViewById(R.id.hospital_nearby);
        restraunt=findViewById(R.id.restraunt_nearby);

        //now create a model class;
        //now create MyHolder class;
        //now create Adapte class;
        //now in the main class

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hospital="hospital", school="shop",restaurant="restaurant";

                String url;
                Object transferData[]=new Object[2];
                GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces();
                mMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
              //  Toast.makeText(About.this, "searching", Toast.LENGTH_SHORT).show();
                //Toast.makeText(About.this, "location show", Toast.LENGTH_SHORT).show();
            }
        });

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hospital="hospital", school="shop",restaurant="restaurant";
                GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces();
                String url;
                Object transferData[]=new Object[2];
                mMap.clear();
                url = getUrl(latitude, longitude, hospital);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
               // Toast.makeText(About.this, "searching", Toast.LENGTH_SHORT).show();
                //Toast.makeText(About.this, "location show", Toast.LENGTH_SHORT).show();

            }
        });
        restraunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hospital="hospital", school="shop",restaurant="restaurant";
                GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces();
                String url;
                Object transferData[]=new Object[2];

                mMap.clear();
                url=getUrl(latitude,longitude,restaurant);
                transferData[0]=mMap;
                transferData[1]=url;

                getNearbyPlaces.execute(transferData);
               // Toast.makeText(About.this, "searching", Toast.LENGTH_SHORT).show();
                //Toast.makeText(About.this, "locatio show", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView=findViewById(R.id.recyclerView);
        preferences=this.getSharedPreferences("My_Pref",MODE_PRIVATE);

        getMyList();



        init();


        // rellenarListView();

        tbUpDown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    tbUpDown.setChecked(true);
                }else if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    tbUpDown.setChecked(false);
                }
            }

            @Override
            public void onSlide(View view, float v) {

            }
        });



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialog();
            }
        });


     /*   btnNearstFood=findViewById(R.id.btnFood);
        btnNearstHospital=findViewById(R.id.btnHospital);
        btnNearstParking=findViewById(R.id.btnParing);

        btnNearstFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(About.this,FreeParkingMap.class));
            }
        });

        btnNearstParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(About.this,FreeParkingMap.class));
            }
        });

        btnNearstHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(About.this,FreeParkingMap.class));
            }
        });*/



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.about);

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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case  R.id.about:
                        return true;
                }

                return false;
            }
        });

       // fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        //fetchLocation();

    }


    //phale// private ArrayList<Model> getMyList() {

    private void getMyList() {
        ArrayList<Model> models=new ArrayList<>();

        Model model=new Model();
        model.setTitle("Clothes Shop");
        model.setDescription("This is a CLothes shop here you can buy cothes");
        model.setImg(R.drawable.clothes);
        models.add(model);

        model=new Model();
        model.setTitle("Jewelly");
        model.setDescription("This is a Jewelly shop here you can buy Jewelly");
        model.setImg(R.drawable.jewelly);
        models.add(model);

        model=new Model();
        model.setTitle("Shop");
        model.setDescription("This is shop here you can buy anything");
        model.setImg(R.drawable.shop);
        models.add(model);

        model=new Model();
        model.setTitle("shops");
        model.setDescription("This is shops here you can buy anything you want");
        model.setImg(R.drawable.shops);
        models.add(model);

        model=new Model();
        model.setTitle("mall");
        model.setDescription("This is Mall here you can you can get all tings");
        model.setImg(R.drawable.mall);
        models.add(model);

        //phale return models;

        String mSortSetting =preferences.getString("Sort","ascending");

        if(mSortSetting.equals("ascending")){
            Collections.sort(models,Model.By_TITLE_ASCENDING);
        }
        else if(mSortSetting.equals("descending")){
            Collections.sort(models,Model.By_TITLE_DESCENDINgG);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//i will create recycleviw in linerarlayout
        myAdapter=new MyAdapter(this,models);
        mRecyclerView.setAdapter(myAdapter);


    }

    private void sortDialog() {
        String[] options ={"Ascending" ,"Descending"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Sort by");
        builder.setIcon(R.drawable.ic_action_sort_foreground);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("Sort","ascending");
                    editor.apply();
                    getMyList();
                }
                if(i==1){

                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("Sort","descending");
                    editor.apply();
                    getMyList();
                }
            }
        });
        builder.create().show();
    }


    public void onClick(View v){

        String hospital="hospital", school="shop",restaurant="restaurant";

        Object transferData[]=new Object[2];
        GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces();

        switch (v.getId()){
            /*case R.id.search_address:
                EditText addressField=findViewById(R.id.location_search);
                String address=addressField.getText().toString();

                List<Address> addressList=null;
                MarkerOptions useRMarkerOptions=new MarkerOptions();

                if(!TextUtils.isEmpty(address)){
                    Geocoder geocoder=new Geocoder(this);
                    try
                    {
                        addressList=geocoder.getFromLocationName(address,6);
                        if(addressList!=null){
                            for(int i=0;i<addressList.size();i++){
                                Address userAddress=addressList.get(i);
                                LatLng latLng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                useRMarkerOptions.position(latLng);
                                useRMarkerOptions.title(address);
                                useRMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(useRMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                        else{
                            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(this, "Please write any location name...", Toast.LENGTH_SHORT).show();
                }
                break;*/

            case R.id.hospital_nearby:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                mMap.clear();
                String url=getUrl(latitude,longitude,hospital);
                transferData[0]=mMap;
                transferData[1]=url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching near by hospital", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing near by hospital", Toast.LENGTH_SHORT).show();
                break;


            case R.id.school_nearby:
                mMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching near by school", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing near by school", Toast.LENGTH_SHORT).show();
                break;


            case R.id.restraunt_nearby:
                mMap.clear();
                url=getUrl(latitude,longitude,restaurant);
                transferData[0]=mMap;
                transferData[1]=url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching near by restarunt", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing near by restarunt", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googleURL=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude+","+longitude);
        googleURL.append("&radius=" + ProxymityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyDJIq4-W6B09EtXLfOMJ28MAjTqRnvghiw");

        Log.d("GooglrmapActivity","url =" +googleURL.toString());

        return googleURL.toString();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public boolean checkUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  Request_User_Location_Code:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient==null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized  void buildGoogleApiClient(){
        googleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude=location.getLatitude();
        longitude=location.getLongitude();

        lastlocation=location;
        if(currentUserLocationMarker!=null){
            currentUserLocationMarker.remove();

        }
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentUserLocationMarker=mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));

        if(googleApiClient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void init() {
        this.linearLayoutBSheet = findViewById(R.id.bottomSheet);
        this.bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBSheet);
        this.tbUpDown = findViewById(R.id.toggleButton);
        // this.listView = findViewById(R.id.recyclerView);
        this.txtCantante = findViewById(R.id.txtCantante);
        this.txtCancion = findViewById(R.id.txtCancion);
        this.progbar = findViewById(R.id.progbar);
    }

          /* private void rellenarListView() {
               String[] nombre = {"50 Cent", "50 Cent", "50 Cent", "50 Cent", "50 Cent", "50 Cent", "50 Cent", "50 Cent"};
               String[] correo = {"Many Men", "Window Shopper",
                       "Candy Shop", "Just a lil bit", "I'm the man", "P.I.M.P", "Wanksta",
                       "Ayo technology"};


               ArrayList<Map<String, Object>> lista = new ArrayList<>();

               int nombresLen = nombre.length;

               for (int i = 0; i < nombresLen; i++) {
                   Map<String, Object> listItem = new HashMap<>();
                   listItem.put("Cantante", nombre[i]);
                   listItem.put("Titulo", correo[i]);

                   lista.add(listItem);
               }

               this.listView.setAdapter(getAdapterListViewCT(lista));

               this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       TextView txtCantanteLV = view.findViewById(android.R.id.text1);
                       TextView txtCancionLV = view.findViewById(android.R.id.text2);

                       txtCantante.setText(txtCantanteLV.getText());
                       txtCancion.setText(txtCancionLV.getText());

                       progbar.setProgress(getRandom());
                   }
               });
           }*/

           /*private SimpleAdapter getAdapterListViewCT(ArrayList<Map<String, Object>> lista) {
               return new SimpleAdapter(this, lista,
                       android.R.layout.simple_list_item_2, new String[]{"Cantante", "Titulo"},
                       new int[]{android.R.id.text1, android.R.id.text2}) {

                   @Override
                   public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                       View view = super.getView(position, convertView, parent);

                       TextView txtNombre = view.findViewById(android.R.id.text1);
                       txtNombre.setTypeface(Typeface.DEFAULT_BOLD);

                       TextView txtCorreo = view.findViewById(android.R.id.text2);
                       txtCorreo.setTextColor(Color.DKGRAY);

                       return view;
                   }

               };
           }*/

   /* private int getRandom() {
        return (int) Math.floor(Math.random() * 100);
    }*/





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
                    supportMapFragment.getMapAsync(About.this);
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

}
