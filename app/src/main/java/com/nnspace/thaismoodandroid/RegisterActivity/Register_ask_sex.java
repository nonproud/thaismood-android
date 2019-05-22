package com.nnspace.thaismoodandroid.RegisterActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.transition.TransitionManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nnspace.thaismoodandroid.R;

public class Register_ask_sex extends Fragment {

    private String sex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_ask_sex, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayout male = getView().findViewById(R.id.register_ask_sex_male_layout);
        final LinearLayout female = getView().findViewById(R.id.register_ask_genter_female_layout);
        final Button nextBtn = getView().findViewById(R.id.register_ask_sex_next_btn);
        final LinearLayout pregnantLayout = getView().findViewById(R.id.register_ask_sex_is_pregnant_layout);
        final ViewGroup transition = getView().findViewById(R.id.register_ask_sex_is_pregnant_layout);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                pregnantLayout.setVisibility(View.GONE);
                male.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                female.setBackgroundColor(getResources().getColor(R.color.non_select_color));
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                sex = "male";
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                pregnantLayout.setVisibility(View.VISIBLE);
                female.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                male.setBackgroundColor(getResources().getColor(R.color.non_select_color));
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                sex = "female";
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("sex", sex);
                bundle.putString("type", "patient");

                if (sex.equals("female")) {
                    RadioGroup isPregnant = getView().findViewById(R.id.register_ask_sex_is_pregnant_radiogroup);
                    RadioButton rd = getView().findViewById(isPregnant.getCheckedRadioButtonId());
                    bundle.putString("isPregnant", rd.getTag().toString());
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment form = new Register_form();
                form.setArguments(bundle);
                ft.replace(R.id.register_fragment_container, form);
                ft.commit();
            }
        });
    }
}