package com.nnspace.thaismoodandroid.MainUI;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.SentRequestToServer.SignOnSentRequestToServer;

import java.util.Map;

public class SignInOn extends AppCompatActivity {

    private Button loginBtn, registBtn;
    private ConstraintLayout login_form, regist_form;
    private Dialog registDialog, loginDialog;
    private final ThaisMoodDB db = new ThaisMoodDB(this);
    private TextView emailWarning, usernameWarning;
    private EditText emailtx, usernametx;
    private Boolean isReadToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                        if(SignOnSentRequestToServer.isUsernameDuplicate(SignInOn.this, usernametx.getText().toString())){
                            usernameWarning.setVisibility(View.VISIBLE);
                        }else{
                            usernameWarning.setVisibility(View.GONE);
                        }
                    }
                });

                emailtx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(SignOnSentRequestToServer.isEmailDuplicate(SignInOn.this, emailtx.getText().toString())){
                            emailWarning.setVisibility(View.VISIBLE);
                        }else{
                            emailWarning.setVisibility(View.GONE);
                        }
                    }
                });

                regist_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText emailtx = registDialog.findViewById(R.id.register_email);
                        EditText passwdtx = registDialog.findViewById(R.id.register_password);
                        String email = emailtx.getText().toString();
                        String password = passwdtx.getText().toString();
                        if(SignOnSentRequestToServer.sendRegistRequest(SignInOn.this, email, password)){
//                            Toast.makeText(SignInOn.this, response.toString(), Toast.LENGTH_LONG).show();
                            registDialog.dismiss();
                            Intent intent = new Intent(SignInOn.this, Otp.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        }else{
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
                        EditText emailtx = loginDialog.findViewById(R.id.login_email);
                        EditText passwdtx = loginDialog.findViewById(R.id.login_password);
                        String email = emailtx.getText().toString();
                        String password = passwdtx.getText().toString();
                        Map<String, Object> result = SignOnSentRequestToServer.sendLoginRequest(SignInOn.this, email, password);
                        if(result.get("result").equals("true")){
                            if(result.get("is_verified").toString().equals("0")){
                                Intent intent = new Intent(SignInOn.this, Otp.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(SignInOn.this, Home2.class);
                                startActivity(intent);
                            }

                            finish();
                        }else{

                        }
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
