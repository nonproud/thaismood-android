package com.nnspace.thaismoodandroid.RegisterActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class Register_form extends Fragment {

    private String type;
    private String sex;
    private boolean isPregnant;
    private TextInputLayout dob;
    TextInputEditText dobTx;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        type = bundle.getString("type");

        if(type.equals("patient")){
            sex = bundle.getString("sex");
            if(sex.equals("famale")){
                if(bundle.get("isPregnant").equals("1")){
                    isPregnant = true;
                }else {
                    isPregnant = false;
                }
            }
        }
        return inflater.inflate(R.layout.fragment_register_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dob = getView().findViewById(R.id.register_dob_text_input);
        dobTx = getView().findViewById(R.id.register_dob);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePickerDialog == null){
                    datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));

                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                datePickerDialog.show();
            }
        });

        dobTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePickerDialog == null){
                    datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));

                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                datePickerDialog.show();
            }
        });

        dobTx.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(datePickerDialog == null){
                        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                dobTx.setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));

                            }
                        }, calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    }
                    datePickerDialog.show();
                }
            }
        });
    }



}
