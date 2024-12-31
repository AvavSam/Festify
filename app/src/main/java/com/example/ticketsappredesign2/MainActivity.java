package com.example.ticketsappredesign2;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigations);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_NAME", getIntent().getStringExtra("USER_NAME"));
                    homeFragment.setArguments(bundle);
                    selectedFragment = homeFragment;
                } else if (itemId == R.id.navigation_tickets) {
                    selectedFragment = new TicketsFragment();
                } else if (itemId == R.id.navigation_profile) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    // Move bundle creation here
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_NAME", getIntent().getStringExtra("USER_NAME"));
                    bundle.putString("USER_EMAIL", getIntent().getStringExtra("USER_EMAIL"));
                    profileFragment.setArguments(bundle);
                    selectedFragment = profileFragment;
                }

                if (selectedFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_layout, selectedFragment);
                    transaction.commit();
                }
                return true;
            }
        });

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
}
