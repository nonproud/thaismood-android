package com.example.thaismoodandroid.MainUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
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
    private String url_register;
    private String url_login;
    private  String url_confirm_email;
    private ConstraintLayout login_form, regist_form;
    private Dialog registDialog, loginDialog;
    private final LogonDatabase db = new LogonDatabase(this);
    private TextView emailWarning;
    private EditText emailtx;
    private Boolean isReadToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_sign_in_on);

        url_register = getResources().getString(R.string.member_url);
        url_login = getResources().getString(R.string.member_login_url);
        url_confirm_email = getString(R.string.member_check_email_duplicate);

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
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignInOn.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(SignInOn.this, response.toString(), Toast.LENGTH_LONG).show();
                db.insertLogonResgist(response.toString(), email);
                registDialog.dismiss();
                Intent intent = new Intent(SignInOn.this, Otp.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInOn.this, error.toString(), Toast.LENGTH_LONG).show();
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
                MyData.put("passwd", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
    }

    public void isEmailDuplicate(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(SignInOn.this);
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
                Toast.makeText(SignInOn.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", emailtx.getText().toString());
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
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
                emailtx = registDialog.findViewById(R.id.register_email);
                final EditText email_confirmtx = registDialog.findViewById(R.id.register_email_confirm);
                final EditText passwdcmtx = registDialog.findViewById(R.id.register_password_confirm);
                Button regist_submit = registDialog.findViewById(R.id.register_submit_btn);
                emailWarning = registDialog.findViewById(R.id.regist_email_waring);

                emailtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        isEmailDuplicate();
                    }
                });

                regist_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText emailtx = registDialog.findViewById(R.id.register_email);
                        EditText passwdtx = registDialog.findViewById(R.id.register_password);
                        String email = emailtx.getText().toString();
                        String password = passwdtx.getText().toString();
                        sendRegistRequest(email, password);
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

                email_confirmtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        EditText emailtx = registDialog.findViewById(R.id.register_email);
                        if(!emailtx.getText().toString().equals(email_confirmtx.getText().toString())){
                            registDialog.findViewById(R.id.regist_confirm_email_waring).setVisibility(View.VISIBLE);
                        }else{
                            registDialog.findViewById(R.id.regist_confirm_email_waring).setVisibility(View.GONE);
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
                        EditText emailtx = loginDialog.findViewById(R.id.login_email);
                        EditText passwdtx = loginDialog.findViewById(R.id.login_password);
                        String email = emailtx.getText().toString();
                        String password = passwdtx.getText().toString();
                        sendLoginRequest(email, password);
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

}
