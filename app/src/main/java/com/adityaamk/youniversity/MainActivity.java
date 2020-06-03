package com.adityaamk.youniversity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SearchFragment.SearchFragmentListener, ChangeDialog.ChangeDialogListener {
    ArrayList<University> universities;
    SearchFragment searchFragment;
    ListFragment listFragment;
    ProfileFragment profileFragment;
    Fragment fragment;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_search:
                fragment = searchFragment;
                break;
            case R.id.navigation_list:
                fragment = listFragment;
                break;
            case R.id.profile_target:
                fragment = profileFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
        listFragment = new ListFragment();
        profileFragment = new ProfileFragment();
        universities = new ArrayList<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.project_id), null);
        Type type = new TypeToken<ArrayList<University>>() {}.getType();
        universities = gson.fromJson(json, type);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void sendData(ArrayList<University> schools) {
        universities.addAll(schools);
        listFragment.updateList(universities);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String json = gson.toJson(universities);
        editor.putString(getString(R.string.project_id), json);
        editor.apply();
    }
}
