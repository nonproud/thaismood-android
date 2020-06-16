package com.nnspace.thaismoodandroid.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nnspace.thaismoodandroid.R;

public class EmergencyContactActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_help);

        Button dmhCallBtn = findViewById(R.id.dmh_call_btn);
        Button dmhFBBtn = findViewById(R.id.dmh_fb_btn);
        Button smrtCallBtn = findViewById(R.id.smrt_call_btn);
        Button smrtWebBtn = findViewById(R.id.smrt_web_btn);
        Button smrtFBBtn = findViewById(R.id.smrt_fb_btn);
        ImageView backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dmhCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = getResources().getString(R.string.dmh_number);
                makeCallPhone();
            }
        });

        dmhFBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com/helpline1323")));
            }
        });

        smrtCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = getResources().getString(R.string.smrt_number);
                makeCallPhone();
            }
        });

        smrtWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.samaritansthai.com")));
            }
        });

        smrtFBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com/Samaritans.Thailand")));
            }
        });
        
    }

    private void makeCallPhone() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(EmergencyContactActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(EmergencyContactActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

        } else {
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    makeCallPhone();
                }
            }
        }
}
