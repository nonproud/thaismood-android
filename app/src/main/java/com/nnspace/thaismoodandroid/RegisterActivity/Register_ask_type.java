package com.nnspace.thaismoodandroid.RegisterActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nnspace.thaismoodandroid.R;

public class Register_ask_type extends Fragment {

    private String selectedType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_ask_type, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayout typeGeneral = getView().findViewById(R.id.register_ask_type_general_layout);
        final LinearLayout typePatient = getView().findViewById(R.id.register_ask_type_patient_layout);
        final Button nextBtn = getView().findViewById(R.id.register_ask_type_next_btn);

        typeGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeGeneral.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                typePatient.setBackgroundColor(getResources().getColor(R.color.non_select_color));
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                selectedType = "general";
            }
        });

        typePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePatient.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                typeGeneral.setBackgroundColor(getResources().getColor(R.color.non_select_color));
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                selectedType = "patient";
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", selectedType);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment nextFragment = null;

                if(selectedType.equals("general")){
                   nextFragment = new Register_form();
                }else if(selectedType.equals("patient")){
                    nextFragment = new Register_ask_sex();
                }

                nextFragment.setArguments(bundle);

                ft.replace(R.id.register_fragment_container, nextFragment);
                ft.commit();
            }
        });
    }
}
