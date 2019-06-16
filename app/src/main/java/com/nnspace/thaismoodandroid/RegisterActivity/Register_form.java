package com.nnspace.thaismoodandroid.RegisterActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.EvaluationActivity.Evaluation;
import com.nnspace.thaismoodandroid.R;
import com.rm.rmswitch.RMSwitch;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register_form extends Fragment {

    private String create_profile_url;
    private String type, sex, isPregnantString, username, dobString, caffeineString, drugString, nicknameString, emergencyContactString;
    private String d1, d2, d3, d4, d5, d6;
    private boolean isPatient = false, isPregnant;
    private TextInputLayout dob;
    private TextInputEditText dobTx, nicknameTx, emergetcyContactTx;
    private Calendar calendar = Calendar.getInstance();
    private LinearLayout patientSection, femaleSection;
    private Context mContext;
    private float weight = 0, height = 0, bmi;
    private short isCaffeine, isDrug;
    private ThaisMoodDB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        type = bundle.getString("type");

        if(type.equals("patient")){
            isPatient = true;
            sex = bundle.getString("sex");
            if(sex.equals("famale")){
                isPregnant = bundle.getBoolean("isPregnant");
            }
        }
        Log.d("isPragnent:", isPregnant + "");
        return inflater.inflate(R.layout.fragment_register_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        create_profile_url = getActivity().getResources().getString(R.string.member_profile_url);
        db = new ThaisMoodDB(getContext());
        username = db.getUsername();
        TextInputEditText usernametx = getView().findViewById(R.id.register_username_text);
        usernametx.setText(username);

        emergetcyContactTx = getView().findViewById(R.id.register_emergency_contact_text);
        nicknameTx = getView().findViewById(R.id.register_nickname_text);

        patientSection = getView().findViewById(R.id.register_patient_section);
        femaleSection = getView().findViewById(R.id.register_female_section);

        if(isPatient){
            patientSection.setVisibility(View.VISIBLE);
            if(isPregnant){
                femaleSection.setVisibility(View.GONE);
            }
        }

        mContext = getContext();
        setUpDobSection();
        setUpWeightSection();
        setUpDiseaseSection();

        LinearLayout saveBtn = getView().findViewById(R.id.register_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prepareDataAndSentToServer();
                if(isPatient){
                    sendCreateProfileRequestToServerForPatient();
                }else {
                    sendCreateProfileRequestToServerForGeneral();
                }
            }
        });

    }

    private void prepareDataAndSentToServer() {

        RMSwitch caffeine = getView().findViewById(R.id.switch_caffeine);
        RMSwitch drug = getView().findViewById(R.id.switch_drug_addict);

        nicknameString = nicknameTx.getText().toString();
        emergencyContactString = emergetcyContactTx.getText().toString();

        isCaffeine = (short) (caffeine.isChecked() ? 1:0);
        isDrug = (short) (drug.isChecked() ? 1:0);

        caffeineString = isCaffeine + "";
        drugString = isDrug + "";

        if(isPatient){
            short isD1, isD2, isD3, isD4, isD5;
            RMSwitch d1s = getView().findViewById(R.id.switch_d1);
            RMSwitch d2s = getView().findViewById(R.id.switch_d2);
            RMSwitch d3s = getView().findViewById(R.id.switch_d3);
            RMSwitch d4s = getView().findViewById(R.id.switch_d4);
            RMSwitch d5s = getView().findViewById(R.id.switch_d5);
            RMSwitch d6s = getView().findViewById(R.id.switch_d6);

            d1 = (d1s.isChecked() ? "1":"0");
            d2 = (d2s.isChecked() ? "1":"0");
            d3 = (d3s.isChecked() ? "1":"0");
            d4 = (d4s.isChecked() ? "1":"0");
            d5 = (d5s.isChecked() ? "1":"0");

            if(d6s.isChecked()){
                TextInputEditText otherString = getView().findViewById(R.id.register_disease_others_text);
                d6 = "1:" + otherString;
            }else{
                d6 = "0";
            }

            if(sex.equals("female")){
                isPregnantString = (isPregnant ? 1:0) + "";
            }else{
                sex = "male";
                isPregnantString = "0";
            }
        }
    }

    private void setUpDobSection(){

        dobTx = getView().findViewById(R.id.register_dob);
        dob = getView().findViewById(R.id.register_dob_text_input);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dobString = year + "/" + (month + 1) +"/" + dayOfMonth;

                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        dobTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dobString = year + "/" + (month + 1) +"/" + dayOfMonth;

                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });



        dobTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            dobString = year + "/" + (month + 1) +"/" + dayOfMonth;

                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            }
        });

    }

    private void setUpWeightSection() {
        final TextInputEditText weightTx = getView().findViewById(R.id.register_weight);
        final TextInputEditText heightTx = getView().findViewById(R.id.register_height);

        weightTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try{
                        weight = Float.parseFloat(weightTx.getText().toString());
                    }catch (Exception e){
                        weight = 0;
                    }

                    if(height != 0){
                        calculateBMI();
                    }
                }
            }
        });

        heightTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    try{
                        height = Float.parseFloat(heightTx.getText().toString());
                    }catch (Exception e){
                        height = 0;
                    }
                    if(weight != 0){
                        calculateBMI();
                    }
                }
            }
        });

    }

    private void setUpDiseaseSection() {
        final RMSwitch dOther = getView().findViewById(R.id.switch_d6);
        final TextInputEditText other = getView().findViewById(R.id.register_disease_others_text);

        dOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dOther.isChecked()) {
                    dOther.setChecked(false);
                    other.setVisibility(View.GONE);
                } else {
                    dOther.setChecked(true);
                    other.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void calculateBMI(){

        TextInputEditText bmiTx = getView().findViewById(R.id.register_bmi_input);
        if(weight == 0|| height == 0){
            bmiTx.setText("");
            return;
        }

        bmi = weight / ((height/100) * (height/100));
        bmiTx.setText(String.format("%.2f", bmi));
    };

    private void sendCreateProfileRequestToServerForPatient(){

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, create_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Toast.makeText(getContext(), "บันทึกข้อมูลแล้ว", Toast.LENGTH_LONG).show();
                    db.updateType("p");
                    db.createProfilePatient(nicknameString, emergencyContactString, dobString, isCaffeine, isDrug, sex, isPregnant ? 1:0, weight, height, bmi, d1, d2, d3, d4, d5, d6);
                    Intent intent = new Intent(getContext(), Evaluation.class);
                    intent.putExtra("todo", 1);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "ผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> a = new HashMap<String, String>();
                a.put("type", "p");
                a.put("username", username);
                a.put("nickname", nicknameString);
                a.put("emergency_contact", emergencyContactString);
                a.put("dob", dobString);
                a.put("is_caffeine_addict", caffeineString);
                a.put("is_drug_addict", drugString);
                a.put("is_pregnant", isPregnantString);
                a.put("weight", weight + "");
                a.put("height", height + "");
                a.put("bmi", bmi + "");
                a.put("sex", sex);
                a.put("d1", d1);
                a.put("d2", d2);
                a.put("d3", d3);
                a.put("d4", d4);
                a.put("d5", d5);
                a.put("d6", d6);
                return a;
            }
        };

        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void sendCreateProfileRequestToServerForGeneral(){

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, create_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Toast.makeText(getContext(), "บันทึกข้อมูลแล้ว", Toast.LENGTH_LONG).show();
                    db.updateType("g");
                    db.createProfileGeneral(nicknameString, emergencyContactString, dobString, isCaffeine, isDrug);
                    Intent intent = new Intent(getContext(), Evaluation.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "ผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> a = new HashMap<>();
                a.put("type", "g");
                a.put("username", username);
                a.put("nickname", nicknameString);
                a.put("emergency_contact", emergencyContactString);
                a.put("dob", dobString);
                a.put("is_caffeine_addict", caffeineString);
                a.put("is_drug_addict", drugString);
                System.out.println(dobString + " - " + caffeineString + " - " + drugString);
                return a;
            }
        };
        System.out.println(myStringRequest.toString());
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
