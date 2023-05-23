package com.example.commutecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;

    private Home homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        homeFragment = new Home();

        bottomNavigationView = findViewById(R.id.navBtn);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id) {
                    case R.id.bottom_home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.bottom_favorite:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.bottom_alarm:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.bottom_profile:
                        viewPager2.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_favorite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_alarm).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }
}