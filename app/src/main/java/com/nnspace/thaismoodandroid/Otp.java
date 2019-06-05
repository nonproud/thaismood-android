package com.nnspace.thaismoodandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.RegisterActivity.Register;

import java.util.HashMap;
import java.util.Map;

public class Otp extends AppCompatActivity {

    private String urlOTPVerify;
    private EditText otpInput;
    private String email, username;
    private final ThaisMoodDB db = new ThaisMoodDB(Otp.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        urlOTPVerify  = getString(R.string.member_otp_verify_url);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getExtras().getString("username");
        TextView emailTextview = findViewById(R.id.otp_email_txv);
        emailTextview.setText("ที่ " + email);

        otpInput = findViewById(R.id.otp_input);

        Button otpSubmit = findViewById(R.id.otp_submit);
        otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateOTP(email, otpInput.getText().toString());
            }
        });

    }

    private void validateOTP(final String email, final String otp){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Otp.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, urlOTPVerify, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    db.updateVerifyStatus();
                    new IOSDialog.Builder(Otp.this)
                            .setMessage("สำเร็จ! อีเมลของคุณถูกยืนยันแล้ว")
                            .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Otp.this, Register.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                }else if(response.toString().equals("0")){
                    ShowDialog.showDialog(Otp.this, "ผิดพลาด! รหัสยืนยันไม่ถูกต้อง");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowDialog.showDialog(Otp.this, "มีบางอย่างผิดพลาด กรุณาลองใหม่อีกครั้ง");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("otp", otp);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}