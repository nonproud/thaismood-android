package com.nnspace.thaismoodandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignInOn extends AppCompatActivity {

    private String url_register;
    private String url_login;
    private String url_confirm_email;
    private String url_confirm_username;

    private Button loginBtn, registBtn;
    private ConstraintLayout login_form, regist_form;
    private Dialog registDialog, loginDialog;
    private final ThaisMoodDB db = new ThaisMoodDB(this);
    private TextView emailWarning, usernameWarning;
    private EditText emailtx, usernametx;
    private Boolean isReadToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url_register = getResources().getString(R.string.member_url);
        url_login = getResources().getString(R.string.member_login_url);
        url_confirm_email = getResources().getString(R.string.member_check_email_duplicate);
        url_confirm_username = getResources().getString(R.string.member_check_username_duplicate);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_sign_in_on);

        registFormInit();
        loginFormInit();
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }


    private void enableRegistSubmitBtn(){

    }

    private void registFormInit(){
        registBtn = (Button) findViewById(R.id.registBtn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registDialog = new Dialog(SignInOn.this);
                registDialog.setContentView(R.layout.dialog_register);
                usernametx = registDialog.findViewById(R.id.register_username);
                emailtx = registDialog.findViewById(R.id.register_email);
                final EditText passwdcmtx = registDialog.findViewById(R.id.register_password_confirm);
                Button regist_submit = registDialog.findViewById(R.id.register_submit_btn);
                emailWarning = registDialog.findViewById(R.id.regist_email_waring);
                usernameWarning = registDialog.findViewById(R.id.regist_username_waring);

                usernametx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        isUsernameDuplicate(usernametx.getText().toString());
                    }
                });

                emailtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        isEmailDuplicate(emailtx.getText().toString());
                    }
                });

                regist_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText usernametx = registDialog.findViewById(R.id.register_username);
                        EditText emailtx = registDialog.findViewById(R.id.register_email);
                        EditText passwdtx = registDialog.findViewById(R.id.register_password);
                        String email = emailtx.getText().toString();
                        String password = passwdtx.getText().toString();
                        String username = usernametx.getText().toString();

                        sendRegisterRequest(username, email, password);
                    }
                });
                TextView close = registDialog.findViewById(R.id.regist_dialog_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registDialog.dismiss();
                    }
                });

                passwdcmtx.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        EditText passwdtx = registDialog.findViewById(R.id.register_password);
                        if(!passwdtx.getText().toString().equals(passwdcmtx.getText().toString())) {
                            registDialog.findViewById(R.id.regist_confirm_password_waring).setVisibility(View.VISIBLE);
                        }else{
                            registDialog.findViewById(R.id.regist_confirm_password_waring).setVisibility(View.GONE);
                        }
                    }
                });

                passwdcmtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText passwdtx = registDialog.findViewById(R.id.register_password);
                        if(!passwdtx.getText().toString().equals(passwdcmtx.getText().toString())) {
                            registDialog.findViewById(R.id.regist_confirm_password_waring).setVisibility(View.VISIBLE);
                        }else if(passwdcmtx.getText().toString().matches("")){
                            registDialog.findViewById(R.id.regist_confirm_password_waring).setVisibility(View.GONE);
                        }else{
                            registDialog.findViewById(R.id.regist_confirm_password_waring).setVisibility(View.GONE);
                        }
                    }
                });

                passwdcmtx.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Button regist_submit = registDialog.findViewById(R.id.register_submit_btn);
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            regist_submit.performClick();
                            return true;
                        }
                        return false;
                    }
                });

                registDialog.show();
            }
        });
    }

    private void loginFormInit(){
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog = new Dialog(SignInOn.this);
                loginDialog.setContentView(R.layout.dialog_login);
                Button login_submit = loginDialog.findViewById(R.id.login_submit_btn);
                login_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText usernametx = loginDialog.findViewById(R.id.login_username);
                        EditText passwdtx = loginDialog.findViewById(R.id.login_password);
                        String username = usernametx.getText().toString();
                        String password = passwdtx.getText().toString();
                        sendLoginRequest(username, password);
                    }
                });
                TextView close = loginDialog.findViewById(R.id.login_dialog_close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginDialog.dismiss();
                    }
                });
                loginDialog.show();
            }
        });
    }

    private void sendRegisterRequest(final String username, final String email, final String password){
        final ThaisMoodDB db = new ThaisMoodDB(SignInOn.this);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignInOn.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                db.insertLogonResgist(username, email);
                Intent intent = new Intent(SignInOn.this, VerifyEmailActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInOn.this);
                builder.setTitle("Error!");
                builder.setMessage("Something went wrong").create();
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
                MyData.put("username", username);
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void sendLoginRequest(final String username, final String password) {
        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
                        System.out.println(map);
                        db.insertLogonLogin(map.get("username"), map.get("email"), map.get("is_verified"), map.get("token"), map.get("type"));

                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(SignInOn.this, MainActivity.class));
                }else{
                    Toast.makeText(SignInOn.this, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void isEmailDuplicate(final String email){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_confirm_email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("1")){
                    emailWarning.setVisibility(View.VISIBLE);
                }else{
                    emailWarning.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void isUsernameDuplicate(final String username) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_confirm_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("1")){
                    usernameWarning.setVisibility(View.VISIBLE);
                }else{
                    usernameWarning.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
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
