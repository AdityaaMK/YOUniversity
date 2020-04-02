package com.adityaamk.youniversity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                selectedFragment = new HomeFragment();
                break;

//            case R.id.navigation_rankings:
//                fragment = rFrag;
//                break;
//
//            case R.id.navigation_search:
//                fragment = searchFrag;
//                break;
//
//            case R.id.navigation_fit:
//                fragment = fFrag;
//                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }
}
