package com.example.thaismoodandroid.MainUI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thaismoodandroid.Database.LogonDatabase;
import com.example.thaismoodandroid.R;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInOn extends AppCompatActivity {

    private Button loginBtn, registBtn;
    private String regist_respones_message = null, login_respones_message = null;
    private final String url_register = getResources().getString(R.string.member_url);
    private final String url_login = getResources().getString(R.string.member_login_url);
    private ConstraintLayout login_form, regist_form;
    private final LogonDatabase db = new LogonDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_sign_in_on);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registBtn = (Button) findViewById(R.id.registBtn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist_form = (ConstraintLayout) findViewById(R.id.register_form);
                ImageView exit = (ImageView) findViewById(R.id.register_exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registFadeout();
                    }
                });
                Button regist_submit = (Button) findViewById(R.id.register_submit_btn);
                regist_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
                        String password = ((EditText) findViewById(R.id.register_password)).getText().toString();
                        sendRegistRequest(email, password);
                    }
                });
                registFadein();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_form = (ConstraintLayout) findViewById(R.id.login_form);
                ImageView exit = (ImageView) findViewById(R.id.login_exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginFadeout();
                    }
                });
                Button loginSubmit = (Button) findViewById(R.id.login_submit_btn);
                EditText a = (EditText) findViewById(R.id.login_email);
                String text = a.getText().toString();

                loginSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
                        String password = ((EditText) findViewById(R.id.login_password)).getText().toString();
                        sendLoginRequest(email, password);
                    }
                });
                loginFadein();
            }
        });
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    private void registFadein() {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.transition.fade);
        regist_form.startAnimation(animation);
        regist_form.setVisibility(View.VISIBLE);
        loginBtn.setClickable(false);
        registBtn.setClickable(false);
    }

    private void registFadeout() {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.transition.fadex);
        regist_form.setAnimation(animation);
        regist_form.setVisibility(View.GONE);
        loginBtn.setClickable(true);
        registBtn.setClickable(true);
    }

    private void loginFadein() {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.transition.fade);
        login_form.startAnimation(animation);
        login_form.setVisibility(View.VISIBLE);
        loginBtn.setClickable(false);
        registBtn.setClickable(false);
    }

    private void loginFadeout() {
        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(this, R.transition.fadex);
        login_form.setAnimation(animation);
        login_form.setVisibility(View.GONE);
        loginBtn.setClickable(true);
        registBtn.setClickable(true);
    }

    private void sendLoginRequest(final String email, final String password) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignInOn.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(SignInOn.this, response.toString(), Toast.LENGTH_LONG).show();
                Map<String, Object> map = new HashMap<String, Object>();
                try {

                    ObjectMapper mapper = new ObjectMapper();



                    // convert JSON string to Map
                    map = mapper.readValue(response.toString(), new TypeReference<Map<String, String>>(){});


                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                db.insertLogonLogin(map.get("id").toString(), email, map.get("status").toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInOn.this);
                builder.setTitle("Error!");
                builder.setMessage(error.getMessage()).create();
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.create();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
    }

    private void sendRegistRequest(final String email, final String password) {
        String a;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignInOn.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(SignInOn.this, response.toString(), Toast.LENGTH_LONG).show();
                db.insertLogonResgist(response.toString(), email);
                registFadeout();
                Intent intent = new Intent(SignInOn.this, Otp.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInOn.this);
                builder.setTitle("Error!");
                builder.setMessage(error.getMessage()).create();
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.create();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
    }

    private void showLoadingDialog() {

    }


}
