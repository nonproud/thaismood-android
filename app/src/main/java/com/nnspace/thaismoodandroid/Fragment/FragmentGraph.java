package com.nnspace.thaismoodandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

public class FragmentGraph extends Fragment {

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

        weekLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekLayout.setBackgroundColor(getResources().getColor(R.color.white));
                monthLayout.setBackgroundColor(0x00000000);
                yearLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_week_header));
            }
        });

        monthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthLayout.setBackgroundColor(getResources().getColor(R.color.white));
                weekLayout.setBackgroundColor(0x00000000);
                yearLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_month_header));
            }
        });

        yearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                weekLayout.setBackgroundColor(0x00000000);
                monthLayout.setBackgroundColor(0x00000000);
                header.setText(getResources().getString(R.string.fragment_graph_year_header));
            }
        });
    }
}
