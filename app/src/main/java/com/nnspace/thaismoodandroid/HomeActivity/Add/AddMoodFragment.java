package com.nnspace.thaismoodandroid.HomeActivity.Add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.MyThaiCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class AddMoodFragment extends Fragment {

    private final Calendar calendar = Calendar.getInstance();
    private final Calendar currentCalendar = Calendar.getInstance();
    private final MyThaiCalender myThaiCalender = new MyThaiCalender();
    private SeekBar moodLevel;
    private EditText dateText;
    private TextView moodLevelBand;
    private ImageView violetEmo, greyEmo, greenEmo, yellowEmo, redEmo;
    private LinearLayout violetLayout, greyLayout, greenLayout, yellowLayout,
            redLayout, moodLevelSection;

    private  Button nextBtn;
    private int selectedMood = 0, moodLevelValue = 1;


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
        nextBtn = getView().findViewById(R.id.add_mood_next_btn);
        moodLevel = getView().findViewById(R.id.add_mood_level_seek_bar);
        moodLevelBand = getView().findViewById(R.id.add_mood_level_mood_level_label);
        moodLevelSection = getView().findViewById(R.id.add_mood_seek_bar_section);

        setMoodLayout();
        setDatePicker();

    }

    private void setDatePicker() {
        String  todayText =  calendar.get(Calendar.DAY_OF_MONTH) + " " +
                MyThaiCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) + " " +
                (calendar.get(Calendar.YEAR) + 543);
        dateText = getView().findViewById(R.id.add_mood_date_select);
        dateText.setText(todayText);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        dateText.setText(dayOfMonth + " " + MyThaiCalender.getMonthOfYear(month) + " " + (year + 543));

                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void clearButton(){
        moodLevelSection.setVisibility(View.GONE);
        switch (selectedMood){
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

    private void setMoodLayout() {

        final ViewGroup transition = getView().findViewById(R.id.add_mood_transition);

        violetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                clearButton();
                violetEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_violet_fill));
                selectedMood = 1;
                moodLevelValue = 1;
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_violet));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_violet));
                moodLevelSection.setVisibility(View.VISIBLE);
            }
        });

        greyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                clearButton();
                moodLevelSection.setVisibility(View.GONE);
                greyEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_grey_fill));
                selectedMood = 2;
                moodLevelValue = 1;
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_grey));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_grey));
                moodLevelSection.setVisibility(View.VISIBLE);

            }
        });

        greenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                clearButton();
                moodLevelSection.setVisibility(View.GONE);
                greenEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_green_fill));
                selectedMood = 3;
                moodLevelValue = 0;
            }
        });

        yellowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                clearButton();
                moodLevelSection.setVisibility(View.GONE);
                yellowEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_yellow_fill));
                selectedMood = 4;
                moodLevelValue = 1;
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_yellow));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_yellow));
                moodLevelSection.setVisibility(View.VISIBLE);
            }
        });

        redLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transition);
                clearButton();
                moodLevelSection.setVisibility(View.GONE);
                redEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_red_fill));
                selectedMood = 5;
                moodLevelValue = 1;
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_red));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_red));
                moodLevelSection.setVisibility(View.VISIBLE);
            }
        });



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThaisMoodDB db = new ThaisMoodDB(getActivity());
                if(db.insertMood(selectedMood, moodLevelValue, getDateString())){
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

        moodLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                moodLevelValue = progress;
                moodLevelBand.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String getDateString(){
        Log.d("date", String.format("%d/%d/%d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        return String.format("%d/%d/%d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }
}
