package com.nnspace.thaismoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nnspace.thaismoodandroid.Fragment.FragmentGraph;
import com.nnspace.thaismoodandroid.Fragment.FragmentList;
import com.nnspace.thaismoodandroid.Fragment.FragmentProfile;
import com.nnspace.thaismoodandroid.Fragment.FragmentMore;

public class Home extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation_bar);

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
                        Intent intent = new Intent(Home.this, AddNewRecord.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_profile:
                        fragment = new FragmentProfile();
                        break;

                    case R.id.navigation_setting:
                        fragment = new FragmentMore();
                        break;
                }

                return loadFragment(fragment);
            }
        });

        navigation.setSelectedItemId(R.id.navigation_graph);

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
