package com.nnspace.thaismoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nnspace.thaismoodandroid.MainUI.Otp;
import com.nnspace.thaismoodandroid.MainUI.SignInOn;

public class MainmenuTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu_test);
        Button evaluation = (Button) findViewById(R.id.evaluation_btn);
        Button inon = (Button) findViewById(R.id.sign_in_onbtn);
        Button main = (Button) findViewById(R.id.mainbtn);
        Button otp = (Button) findViewById(R.id.otpbtn);
        Button register = (Button) findViewById(R.id.register_activity_btn);

        evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Evaluation.class);
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
