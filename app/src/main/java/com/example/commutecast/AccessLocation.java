package com.example.commutecast;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AccessLocation extends AppCompatActivity {

    private MaterialButton multiplePermissionBtn;
    private TextView geoLocation;
    private static final String TAG = "PERMISSION_TAG";

    /*Button button_location;
    TextView textView_location;
    LocationManager locationManager;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_location);

        getSupportActionBar().hide();

        /*textView_location = findViewById(R.id.tvGeoLocation);
        button_location = findViewById(R.id.continueBtn);

        if(ContextCompat.checkSelfPermission(AccessLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AccessLocation.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, AccessLocation.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, AccessLocation.this);*/

         multiplePermissionBtn = findViewById(R.id.continueBtn);
        geoLocation = findViewById(R.id.tvGeoLocation);


        multiplePermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};

            permissionLauncherMultiple.launch(permission);
            }
        });
    }

    /*private ActivityResultLauncher<String> permissionLauncherSingle = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    Log.d(TAG, "onActivityResult: isGranted: " + isGranted);

                    if(isGranted) {

                    }
                    else {
                        Log.d(TAG, "onActivityResult: Permission denied...");
                        Toast.makeText(AccessLocation.this, "Permission denied...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );*/

    private ActivityResultLauncher<String[]> permissionLauncherMultiple = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {

                    boolean allAreGranted = true;
                    for(Boolean isGranted : result.values()) {
                        Log.d(TAG, "onActivityResult: isGranted: " + isGranted);
                        allAreGranted = allAreGranted && isGranted;
                    }

                    if(allAreGranted) {
                        multiplePermissionsGranted();
                    }
                    else {
                        Log.d(TAG, "onActivityResult: All or some permissions denied...");
                        Toast.makeText(AccessLocation.this, "All are some permissions denied...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    /*private void singlePermissionGranted() {
        geoLocation.setText("Single permission granted. You can do your tasks...");
    }*/
    private void multiplePermissionsGranted() {
        geoLocation.setText("All Permissions granted. You can do your tasks...");
    }

    /*@Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+ location.getLatitude()+","+ location.getLongitude(), Toast.LENGTH_SHORT).show();
    try {
        Geocoder geocoder = new Geocoder(AccessLocation.this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        String address = addresses.get(0).getAddressLine(0);

        textView_location.setText(address);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }*/
}