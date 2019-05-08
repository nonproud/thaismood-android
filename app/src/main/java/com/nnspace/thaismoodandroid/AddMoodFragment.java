package com.nnspace.thaismoodandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddMoodFragment extends Fragment {

    private ImageView violetEmo, greyEmo, greenEmo, yellowEmo, redEmo;
    private LinearLayout violetLayout, greyLayout, greenLayout, yellowLayout,
            redLayout, descriptionLayout;
    private TextView descriptionText;
    private int currentPressed = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_mood, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        violetEmo = getView().findViewById(R.id.add_mood_emo_violet_icon);
        greyEmo = getView().findViewById(R.id.add_mood_emo_grey_icon);
        greenEmo = getView().findViewById(R.id.add_mood_emo_green_icon);
        yellowEmo = getView().findViewById(R.id.add_mood_emo_yellow_icon);
        redEmo = getView().findViewById(R.id.add_mood_emo_red_icon);
        violetLayout = getView().findViewById(R.id.add_mood_emo_violet_layout);
        greyLayout = getView().findViewById(R.id.add_mood_emo_grey_layout);
        greenLayout = getView().findViewById(R.id.add_mood_emo_green_layout);
        yellowLayout = getView().findViewById(R.id.add_mood_emo_yellow_layout);
        redLayout = getView().findViewById(R.id.add_mood_emo_red_layout);
        Button nextBtn = getView().findViewById(R.id.add_mood_next_btn);

        descriptionLayout = getView().findViewById(R.id.add_mood_description_emo_section);
        descriptionText = getView().findViewById(R.id.add_mood_description_emo_text);

        final ViewGroup transition = getView().findViewById(R.id.add_mood_transition);

        violetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton();
                TransitionManager.beginDelayedTransition(transition);
                descriptionLayout.setVisibility(View.GONE);
                violetEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_violet_fill));
                currentPressed = 1;
                descriptionLayout.setBackground(getResources().getDrawable(R.drawable.mood_box_violet));
                descriptionText.setText(getResources().getString(R.string.add_mood_violet_description));
                descriptionLayout.setVisibility(View.VISIBLE);
            }
        });

        greyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton();
                TransitionManager.beginDelayedTransition(transition);
                descriptionLayout.setVisibility(View.GONE);
                greyEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_grey_fill));
                currentPressed = 2;
                descriptionLayout.setBackground(getResources().getDrawable(R.drawable.mood_box_grey));
                descriptionText.setText(getResources().getString(R.string.add_mood_grey_description));
                descriptionLayout.setVisibility(View.VISIBLE);

            }
        });

        greenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton();
                TransitionManager.beginDelayedTransition(transition);
                descriptionLayout.setVisibility(View.GONE);
                greenEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_green_fill));
                currentPressed = 3;
                descriptionLayout.setBackground(getResources().getDrawable(R.drawable.mood_box_green));
                descriptionText.setText(getResources().getString(R.string.add_mood_green_description));
                descriptionLayout.setVisibility(View.VISIBLE);
            }
        });

        yellowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton();
                TransitionManager.beginDelayedTransition(transition);
                descriptionLayout.setVisibility(View.GONE);
                yellowEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_yellow_fill));
                currentPressed = 4;
                descriptionLayout.setBackground(getResources().getDrawable(R.drawable.mood_box_yellow));
                descriptionText.setText(getResources().getString(R.string.add_mood_yellow_description));
                descriptionLayout.setVisibility(View.VISIBLE);
            }
        });

        redLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton();
                TransitionManager.beginDelayedTransition(transition);
                descriptionLayout.setVisibility(View.GONE);
                redEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_red_fill));
                currentPressed = 5;
                descriptionLayout.setBackground(getResources().getDrawable(R.drawable.mood_box_red));
                descriptionText.setText(getResources().getString(R.string.add_mood_red_description));
                descriptionLayout.setVisibility(View.VISIBLE);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("mood", currentPressed);
                Fragment addLevelFragement = new AddMoodLevelFragment();
                addLevelFragement.setArguments(bundle);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.add_new_record_fragment_container, addLevelFragement);
                ft.commit();
            }
        });
    }

    private void clearButton(){
        switch (currentPressed){
            case 1:
                violetEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_violet_blank));
                break;
            case 2:
                greyEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_grey_blank));
                break;
            case 3:
                greenEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_green_blank));
                break;
            case 4:
                yellowEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_yellow_blank));
                break;
            case 5:
                redEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_red_blank));
                break;
                default:
                    return;
        }
    }
}
