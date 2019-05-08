package com.nnspace.thaismoodandroid;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.nnspace.thaismoodandroid.Fragment.Register_ask_type;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Register extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private String selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fragmentContainer = (FrameLayout) findViewById(R.id.register_fragment_container);

        Fragment askType = new Register_ask_type();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.register_fragment_container, askType);
        ft.commit();


    }

    private void sendCreateProfileRequest(){

    }
}
