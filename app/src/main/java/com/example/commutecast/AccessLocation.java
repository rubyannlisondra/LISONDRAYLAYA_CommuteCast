package com.example.commutecast;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Permission;

public class AccessLocation extends AppCompatActivity /*implements LocationListener*/ {

    Button button_continue;
    Button button_skip;
    TextView textView_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_location);

        getSupportActionBar().hide();

        textView_location = findViewById(R.id.textView1);
        button_skip = findViewById(R.id.button1);
        button_continue = findViewById(R.id.button2);

        /*if(ContextCompat.checkSelfPermission(AccessLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AccessLocation.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

    }
    private void getLocation() {
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

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
    }
    /*private void getLocation() {
        PermissionListener permissionListener = new PermissionListener*/
    }
}