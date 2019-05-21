package com.nnspace.thaismoodandroid.HomeActivity.Add;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nnspace.thaismoodandroid.R;

public class AddNewRecord extends AppCompatActivity {

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        fragmentContainer = (FrameLayout) findViewById(R.id.add_new_record_fragment_container);

        Fragment addMood = new AddMoodFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.add_new_record_fragment_container, addMood);
        ft.commit();
    }
}
