package com.nnspace.thaismoodandroid.HomeActivity.Add;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nnspace.thaismoodandroid.R;

public class AddNewRecord extends AppCompatActivity {

    private int activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        Intent intent = getIntent();

        activityType = intent.getExtras().getInt("type");

        Fragment toLoad = null;

        switch (activityType){
            case 1: // Activity
                break;
            case 2: // Sleep
                toLoad = new AddSleepFragment();
                break;
            case 3: // Mood
                toLoad = new AddMoodFragment();
                break;
            case 5: // Exercise
                break;
        }



        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.add_new_record_fragment_container, toLoad);
        ft.commit();
    }
}
