package com.nnspace.thaismoodandroid.HomeActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nnspace.thaismoodandroid.R;

public class FragmentEmergencyData extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emergency_data, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button dmhCallBtn = getView().findViewById(R.id.dmh_call_btn);
        Button dmhFBBtn = getView().findViewById(R.id.dmh_fb_btn);
        Button smrtCallBtn = getView().findViewById(R.id.smrt_call_btn);
        Button smrtWebBtn = getView().findViewById(R.id.smrt_web_btn);
        Button smrtFBBtn = getView().findViewById(R.id.smrt_fb_btn);

        dmhCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = getActivity().getResources().getString(R.string.dmh_number);
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
                phoneNumber = getActivity().getResources().getString(R.string.smrt_number);
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

    private void makeCallPhone(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

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
