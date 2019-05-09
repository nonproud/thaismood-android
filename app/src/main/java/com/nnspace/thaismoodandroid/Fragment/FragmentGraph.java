package com.nnspace.thaismoodandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.GraphMonth;
import com.nnspace.thaismoodandroid.GraphWeek;
import com.nnspace.thaismoodandroid.GraphYear;
import com.nnspace.thaismoodandroid.R;

public class FragmentGraph extends Fragment {

    private FragmentManager fm;
    private FragmentTransaction ft;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LinearLayout weekLayout = getView().findViewById(R.id.fragment_graph_week_button);
        final LinearLayout monthLayout = getView().findViewById(R.id.fragment_graph_month_button);
        final LinearLayout yearLayout = getView().findViewById(R.id.fragment_graph_year_button);
        final TextView header = getView().findViewById(R.id.fragment_graph_header);


        fm = getActivity().getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment nextFragment = new GraphWeek();
        ft.replace(R.id.graph_fragment_container, nextFragment);
        ft.commit();

        weekLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekLayout.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                monthLayout.setBackgroundColor(0x00000000);
                yearLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_week_header));
                ft = fm.beginTransaction();
                Fragment nextFragment = new GraphWeek();
                ft.replace(R.id.graph_fragment_container, nextFragment);
                ft.commit();
            }
        });

        monthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthLayout.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                weekLayout.setBackgroundColor(0x00000000);
                yearLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_month_header));
                ft = fm.beginTransaction();
                Fragment nextFragment = new GraphMonth();
                ft.replace(R.id.graph_fragment_container, nextFragment);
                ft.commit();
            }
        });

        yearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearLayout.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                weekLayout.setBackgroundColor(0x00000000);
                monthLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_year_header));
                ft = fm.beginTransaction();
                Fragment nextFragment = new GraphYear();
                ft.replace(R.id.graph_fragment_container, nextFragment);
                ft.commit();
            }
        });
    }
}
