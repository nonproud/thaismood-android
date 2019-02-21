package com.example.thaismoodandroid.PartUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thaismoodandroid.R;

import java.util.HashMap;
import java.util.Map;

public class register_form extends AppCompatActivity {

    private EditText name;
    private EditText lname;
    private EditText email;
    private EditText email_confirm;
    private EditText password;
    private EditText password_confirm;
    private Button submit_button;
    private final String url = "http://192.168.2.33:4553/member";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.5));

        name = (EditText) findViewById(R.id.name);
        lname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        email_confirm = (EditText) findViewById(R.id.email_confirm);
        password = (EditText) findViewById(R.id.password);
        password_confirm = (EditText) findViewById(R.id.password_confirm);
        submit_button = (Button) findViewById(R.id.submit_btn);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_name = name.getText().toString();
                final String str_lname = lname.getText().toString();
                final String email_1 = email.getText().toString();
                String email_2 = email_confirm.getText().toString();
                final String pass1 = password.getText().toString();
                String pass2 = password_confirm.getText().toString();

                if(validateEmail(email_1, email_2)){
                    if(validatePassword(pass1, pass2)){
                        RequestQueue MyRequestQueue = Volley.newRequestQueue(register_form.this);
                        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            protected Map<String, String> getParams() {
                                Map<String, String> MyData = new HashMap<String, String>();
                                MyData.put("name", str_name);
                                MyData.put("lastname", str_lname);
                                MyData.put("email", email_1);
                                MyData.put("password", pass1);
                                return MyData;
                            }
                        };
                        MyRequestQueue.add(myStringRequest);
                    }else {

                    }
                }else{

                }
            }
        });

    }

    private boolean validateEmail(String email1, String email2){
        return email1.equals(email2);
    }

    private boolean validatePassword(String password1, String password2){
        return password1.equals(password2);
    }
}
