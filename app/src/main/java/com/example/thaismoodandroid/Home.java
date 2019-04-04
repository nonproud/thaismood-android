package com.example.thaismoodandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Home extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()){
                    case R.id.navigation_oneitem:
                        fragment = new FragmentList();
                        break;

                    case R.id.navigation_graph:
                        fragment = new FragmentGraph();
                        break;

                    case R.id.navigation_add:
                        fragment = new FragmentAdd();
                        break;

                    case R.id.navigation_profile:
                        fragment = new FragmentProfile();
                        break;

                    case R.id.navigation_setting:
                        fragment = new FragmentSetting();
                        break;
                }

                return loadFragment(fragment);
            }
        });

        navigation.setSelectedItemId(R.id.navigation_add);
//        loadFragment(new FragmentAdd());

    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }
}
