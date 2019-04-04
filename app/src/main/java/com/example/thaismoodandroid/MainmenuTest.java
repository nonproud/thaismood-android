package com.example.thaismoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.thaismoodandroid.MainUI.Otp;
import com.example.thaismoodandroid.MainUI.SignInOn;

public class MainmenuTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu_test);
        Button _2q = (Button) findViewById(R.id._2qbtn);
        Button _9q = (Button) findViewById(R.id._9qbtn);
        Button _8q = (Button) findViewById(R.id._8qbtn);
        Button mdq = (Button) findViewById(R.id._mdbbtn);
        Button inon = (Button) findViewById(R.id.sign_in_onbtn);
        Button main = (Button) findViewById(R.id.mainbtn);
        Button otp = (Button) findViewById(R.id.otpbtn);
        Button register = (Button) findViewById(R.id.register_activity_btn);

        _2q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Q2Q.class);
                startActivity(intent);
            }
        });

        _9q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Q9Q.class);
                startActivity(intent);
            }
        });

        _8q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Q8Q.class);
                startActivity(intent);
            }
        });

        mdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, QMDQ.class);
                startActivity(intent);
            }
        });

        inon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, SignInOn.class);
                startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Home.class);
                startActivity(intent);
            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Otp.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Register.class);
                startActivity(intent);
            }
        });

    }
}
