package com.nnspace.thaismoodandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

public class Register_form extends Fragment {

    private String type;
    private String sex;
    private boolean isPregnant;

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

        final LinearLayout patientSection = getView().findViewById(R.id.register_patient_section);
        final LinearLayout periodSection = getView().findViewById(R.id.register_period_section);
        final Button nextBtn = getView().findViewById(R.id.register_submit_btn);
        final LinearLayout pregnantLayout = getView().findViewById(R.id.register_ask_sex_is_pregnant_layout);
        final LinearLayout isTreatSection =getView().findViewById(R.id.register_still_treat_section);

        if(type.equals("patient")){
            isTreatSection.setVisibility(View.VISIBLE);
            patientSection.setVisibility(View.VISIBLE);
            if(sex.equals("female")){
                if(isPregnant) {
                    periodSection.setVisibility(View.GONE);
                }else{
                    periodSection.setVisibility(View.VISIBLE);
                }
            }
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nametx = getView().findViewById(R.id.register_name_text);
                TextView lnametx = getView().findViewById(R.id.register_lastname_text);
                TextView dobtx = getView().findViewById(R.id.register_dob);
                RadioGroup allergicRg = getView().findViewById(R.id.register_allergic_radio_group);
                RadioGroup caffeineRg = getView().findViewById(R.id.register_caffeine_radio_group);
                RadioGroup acdictRg = getView().findViewById(R.id.register_acdict_radio_group);
                RadioButton allerigRd = getView().findViewById(allergicRg.getCheckedRadioButtonId());
                RadioButton caffeineRd = getView().findViewById(caffeineRg.getCheckedRadioButtonId());
                RadioButton acdictRd = getView().findViewById(acdictRg.getCheckedRadioButtonId());

                String name = nametx.getText().toString();
                String lname = lnametx.getText().toString();
                String dob = dobtx.getText().toString();
                String isAllergic = allerigRd.getTag().toString();
                String isCaffeine = caffeineRd.getTag().toString();
                String isAcdict = acdictRd.getTag().toString();

                if(type.equals("patient")){
                    Spinner disorder = getView().findViewById(R.id.register_patient_disorder);

                }
            }
        });

    }



}
