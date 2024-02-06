package com.example.muziko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.muziko.fragment.AboutMeFragment;
import com.example.muziko.fragment.ExploreFragment;
import com.example.muziko.fragment.LibraryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LibraryActivity extends AppCompatActivity {
BottomNavigationView mnBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        mnBottom = findViewById(R.id.navMenu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thư viện");
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Load Fragment
        mnBottom.setOnItemSelectedListener(getListener());
    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener getListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnLibrary:
                        loadFragment(new LibraryFragment());
                        return true;
                    case R.id.mnExplore:
                        loadFragment(new ExploreFragment());
                        return true;
                    case R.id.mnSetting:
                        loadFragment(new AboutMeFragment());
                }
                return true;
            }
        };
    }
        void loadFragment(Fragment fmNew)
        {
            FragmentTransaction fmTran= getSupportFragmentManager().beginTransaction();
            fmTran.replace(R.id.main_fragment, fmNew);
            fmTran.addToBackStack(null);
            fmTran.commit();
        }
    }
