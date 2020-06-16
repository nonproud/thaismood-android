package com.nnspace.thaismoodandroid.Activities;

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
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.ShowDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VerifyEmailActivity extends AppCompatActivity {

    private String urlOTPVerify;
    private EditText otpInput;
    private String email;
    private final ThaisMoodDB db = new ThaisMoodDB(VerifyEmailActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        urlOTPVerify  = getString(R.string.member_otp_verify_url);

        email = db.getEmail();
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
        RequestQueue MyRequestQueue = Volley.newRequestQueue(VerifyEmailActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, urlOTPVerify, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    db.updateVerifyStatus(1);
                    Map<String, String> map = new HashMap<String, String>();
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
                        System.out.println(map);
                        db.updateToken(map.get("token"));

                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    db.updateToken(map.get("token"));
                    new IOSDialog.Builder(VerifyEmailActivity.this)
                            .setMessage("สำเร็จ! อีเมลของคุณถูกยืนยันแล้ว")
                            .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(VerifyEmailActivity.this, Register.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();

                }else if(response.toString().equals("0")){
                    new IOSDialog.Builder(VerifyEmailActivity.this)
                            .setMessage("ผิดพลาด! รหัสยืนยันไม่ถูกต้อง")
                            .setPositiveButton("ตกลง", null)
                            .show();
                    ;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowDialog.showDialog(VerifyEmailActivity.this, "มีบางอย่างผิดพลาด กรุณาลองใหม่อีกครั้ง");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("verifyPassword", otp);
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