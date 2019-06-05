package com.nnspace.thaismoodandroid.RegisterActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

    private String type, sex, username;
    private boolean isPatient = false, isPregnant;
    private TextInputLayout dob;
    private TextInputEditText dobTx;
    private Calendar calendar = Calendar.getInstance();
    private LinearLayout patientSection, femaleSection;
    private Context mContext;
    private float weight = 0, height = 0, bmi;
    private short isCaffeine, isDrug;
    private String dobString;
    private ThaisMoodDB db;
    private Map<String, String> dataToSent;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new ThaisMoodDB(getContext());
        username = db.getUsername();
        TextInputEditText usernametx = getView().findViewById(R.id.register_username_text);
        usernametx.setText(username);

        patientSection = getView().findViewById(R.id.register_patient_section);
        femaleSection = getView().findViewById(R.id.register_female_section);

        if(isPatient){
            patientSection.setVisibility(View.VISIBLE);
            if(isPregnant){
                femaleSection.setVisibility(View.GONE);
            }
        }

        mContext = getContext();
        setUpDobSectoin();
        setUpWeightSection();
        setUpDiseaseSection();

        LinearLayout saveBtn = getView().findViewById(R.id.register_submit_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prepareDataAndSentToServer();

            }
        });

    }

    private void prepareDataAndSentToServer() {

        dataToSent = new HashMap<String, String>();
        dataToSent.put("username", username);
        dataToSent.put("dob", dobString);

        RMSwitch caffeine = getView().findViewById(R.id.switch_caffeine);
        RMSwitch drug = getView().findViewById(R.id.switch_drug_addict);

        isCaffeine = (short) (caffeine.isChecked() ? 1:0);
        isDrug = (short) (drug.isChecked() ? 1:0);

        dataToSent.put("is_caffeine_addict", isCaffeine + "");
        dataToSent.put("is_drug_addict", isDrug + "");

        if(isPatient){
            dataToSent.put("type", "p");
            short isD1, isD2, isD3, isD4, isD5;
            String disease = "";
            RMSwitch d1 = getView().findViewById(R.id.switch_d1);
            RMSwitch d2 = getView().findViewById(R.id.switch_d2);
            RMSwitch d3 = getView().findViewById(R.id.switch_d3);
            RMSwitch d4 = getView().findViewById(R.id.switch_d4);
            RMSwitch d5 = getView().findViewById(R.id.switch_d5);
            RMSwitch d6 = getView().findViewById(R.id.switch_d6);

            isD1 = (short) (d1.isChecked() ? 1:0);
            isD2 = (short) (d2.isChecked() ? 1:0);
            isD3 = (short) (d3.isChecked() ? 1:0);
            isD4 = (short) (d4.isChecked() ? 1:0);
            isD5 = (short) (d5.isChecked() ? 1:0);

            if(d6.isChecked()){
                TextInputEditText otherString = getView().findViewById(R.id.register_disease_others_text);
                disease = String.format("%d:%d:%d:%d:%d:%d:%s", isD1, isD2, isD3, isD4, isD5, 1, otherString.getText().toString());
            }else{
                disease = String.format("%d:%d:%d:%d:%d:%d", isD1, isD2, isD3, isD4, isD5, 0);
            }

            dataToSent.put("disease", disease);
            String isPregnantData = "";
            if(sex.equals("female")){
                dataToSent.put("sex", "female");
                dataToSent.put("is_pregnant", (isPregnant ? 1:0) + "");
                isPregnantData = (isPregnant ? 1:0) + "";
            }else{
                dataToSent.put("sex", "male");
                dataToSent.put("is_pregnant", "0");
            }

            sendCreateProfileRequestToServerForPateint(username, dobString, sex, isPregnantData, disease, isCaffeine + " ", isDrug + "");

        }else {
            dataToSent.put("type", "p");
            sendCreateProfileRequestToServerForGeneral(username, dobString, isCaffeine + "", isDrug + "");
        }
        startActivity(new Intent(getContext(), Evaluation.class));
    }

    private void setUpDobSectoin(){

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

                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    private void sendCreateProfileRequestToServerForPateint(final String username, final String dob, final String sex, final String ispregnent,
                                                            final String disaese, final String caffeine, final String drug){

        final String create_profile_url = "https://thaismood.nn-space.me/member/profile";

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, create_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Intent intent = new Intent(getContext(), Evaluation.class);
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
                a.put("username", username);
                a.put("dob", dob);
                a.put("is_caffeine_addict", caffeine);
                a.put("is_drug_addict", drug);
                a.put("is_pregnant", ispregnent);
                a.put("disease", disaese);
                a.put("weight", weight + "");
                a.put("height", height + "");
                a.put("bmi", bmi + "");
                a.put("sex", sex);
                return a;
            }
        };

        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void sendCreateProfileRequestToServerForGeneral(final String username, final String dob, final String caffeine, final String drug){

        final String create_profile_url = "https://thaismood.nn-space.me/member/profile";

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, create_profile_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
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
                Map<String, String> a = new HashMap<String, String>();
                a.put("username", username);
                a.put("dob", dob);
                a.put("is_caffeine_addict", caffeine);
                a.put("is_drug_addict", drug);
                return a;
            }
        };

        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
