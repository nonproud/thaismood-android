package com.example.thaismoodandroid;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Register extends AppCompatActivity {

    private DatePickerDialog mDatePicker;
    private final Calendar myCalendar = Calendar.getInstance();
    private EditText dobtx;


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dobtx = findViewById(R.id.register_dob);

        RadioGroup gender = findViewById(R.id.register_gender_radio_group);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioGroup isPregnant = findViewById(R.id.register_is_pregnant_radio_group);
                LinearLayout periodSection = findViewById(R.id.register_period_section);
                switch (checkedId){
                    case R.id.register_gender_radio_group_male:
                        isPregnant.setVisibility(View.GONE);
                        periodSection.setVisibility(View.GONE);
                        break;

                    case R.id.register_gender_radio_group_female:
                        isPregnant.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        RadioGroup isPregnantRadio = findViewById(R.id.register_is_pregnant_radio_group);
        isPregnantRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout periodSection = findViewById(R.id.register_period_section);
                switch (checkedId){
                    case R.id.register_is_pregnant_radio_group_no:
                        periodSection.setVisibility(View.VISIBLE);
                        break;

                    case R.id.register_is_pregnant_radio_group_yes:
                        periodSection.setVisibility(View.GONE);
                        break;
                }
            }
        });

        RadioGroup type = findViewById(R.id.register_type_radio_group);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout patientSection = findViewById(R.id.register_patient_section);
                LinearLayout stillTreatSection = findViewById(R.id.register_still_treat_section);
                switch (checkedId){
                    case R.id.register_type_radio_group_general:
                        patientSection.setVisibility(View.GONE);
                        stillTreatSection.setVisibility(View.GONE);
                        break;

                    case R.id.register_type_radio_group_patient:
                        patientSection.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        RadioGroup stillTreat = findViewById(R.id.register_is_still_treat_radio_group);
        stillTreat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout stillTreatSection = findViewById(R.id.register_still_treat_section);
                switch (checkedId){
                    case R.id.register_is_still_treat_radio_group_no:
                        stillTreatSection.setVisibility(View.GONE);
                        break;

                    case R.id.register_is_still_treat_radio_group_yes:
                        stillTreatSection.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}
