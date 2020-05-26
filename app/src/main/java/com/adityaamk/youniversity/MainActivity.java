package com.adityaamk.youniversity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SearchFragment.SearchFragmentListener, ChangeDIalog.ChangeDialogListener {
    ArrayList<University> universities;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    ListFragment listFragment;
    GraphFragment graphFragment;
    Fragment fragment;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = homeFragment;
                break;
            case R.id.navigation_search:
                fragment = searchFragment;
                break;
            case R.id.navigation_list:
                fragment = listFragment;
                break;
            case R.id.navigation_target:
                fragment = graphFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        searchFragment = new SearchFragment();
        listFragment = new ListFragment();
        graphFragment = new GraphFragment();
        universities = new ArrayList<>();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void sendData(ArrayList<University> schools) {
        universities.addAll(schools);
        listFragment.updateList(universities);
        graphFragment.updateList(universities);
    }

    @Override
    public void applyTexts(String posOne, String posTwo) {
        int one = Integer.parseInt(posOne);
        int two = Integer.parseInt(posTwo);
        University temp = universities.get(one-1);
        University temp2 = universities.get(two-1);
        universities.set(one-1,temp2);
        universities.set(two-1,temp);
        listFragment.updateList2(universities);
    }
}
