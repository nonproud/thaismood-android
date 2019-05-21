package com.nnspace.thaismoodandroid.RegisterActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    }



}
