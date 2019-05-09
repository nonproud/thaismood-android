package com.nnspace.thaismoodandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.Database.EmotionDB;

public class AddMoodLevelFragment extends Fragment {

    private int selectedMood;
    private SeekBar moodLevel;
    private int moodLevelValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        selectedMood = bundle.getInt("mood");
        return inflater.inflate(R.layout.fragment_add_mood_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView moodEmo = getView().findViewById(R.id.add_mood_level_emo);
        TextView textdes = getView().findViewById(R.id.add_mood_level_text_des);
        final TextView moodLavelText = getView().findViewById(R.id.add_mood_level_mood_level_label);
        LinearLayout seekbarSection = getView().findViewById(R.id.add_mood_level_seek_bar_section);
        moodLevel = getView().findViewById(R.id.add_mood_level_seek_bar);
        Button nextbtn = getView().findViewById(R.id.add_mood_level_next_btn);
        switch (selectedMood){
            case 1:
                moodEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_violet_fill));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_violet));
                textdes.setText(getResources().getString(R.string.add_mood_text_violet));
                break;
            case 2:
                moodEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_grey_fill));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_grey));
                textdes.setText(getResources().getString(R.string.add_mood_text_grey));
                break;
            case 3:
                moodEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_green_fill));
                moodLevel.setVisibility(View.GONE);
                textdes.setText(getResources().getString(R.string.add_mood_text_green));
                moodLevelValue = 0;
                seekbarSection.setVisibility(View.GONE);
                break;
            case 4:
                moodEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_yellow_fill));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_yellow));
                textdes.setText(getResources().getString(R.string.add_mood_text_yellow));
                break;
            case 5:
                moodEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_red_fill));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_red));
                textdes.setText(getResources().getString(R.string.add_mood_text_red));
                break;
        }

        moodLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moodLevelValue = progress;
                moodLavelText.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText notetx = getView().findViewById(R.id.add_mood_level_note);
                String note = notetx.getText().toString();
                EmotionDB db = new EmotionDB(getActivity());
                if(db.insertMood(selectedMood, moodLevelValue, note)){
                    getActivity().finish();
                }else{
                    try {
                        throw new Exception("ERROR");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}
