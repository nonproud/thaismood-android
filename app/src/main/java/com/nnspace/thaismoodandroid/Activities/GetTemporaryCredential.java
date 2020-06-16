package com.nnspace.thaismoodandroid.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.util.HashMap;
import java.util.Map;

public class GetTemporaryCredential extends AppCompatActivity {

    private TextView tempPassword;
    private String url;
    private ThaisMoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_temporary_credential);
        db = new ThaisMoodDB(GetTemporaryCredential.this);

        url = getResources().getString(R.string.member_temp_password);
        tempPassword = findViewById(R.id.temp_password);
        Button newPassword = findViewById(R.id.new_btn);
        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetTempPasswordToServer();
            }
        });

        ImageView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void sendGetTempPasswordToServer(){
        RequestQueue queue = Volley.newRequestQueue(GetTemporaryCredential.this);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tempPassword.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tempPassword.setText("XX");
                Toast.makeText(GetTemporaryCredential.this, error.toString(), Toast.LENGTH_LONG);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", db.getUsername());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("authorization", db.getToken());
                return params;
            }
        };
        queue.add(sr);
    }

}
