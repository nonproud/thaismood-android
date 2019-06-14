package com.nnspace.thaismoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nnspace.thaismoodandroid.EvaluationActivity.Evaluation;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;
import com.nnspace.thaismoodandroid.RegisterActivity.Register;

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
        Button main2 = (Button) findViewById(R.id.main2_activity_btn);

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

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, VerifyEmailActivity.class);
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

        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainmenuTest.this, Home2.class);
                startActivity(intent);
            }
        });

    }
}
