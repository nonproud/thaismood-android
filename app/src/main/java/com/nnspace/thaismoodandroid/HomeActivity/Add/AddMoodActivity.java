package com.nnspace.thaismoodandroid.HomeActivity.Add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.TransitionManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.MyCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddMoodActivity extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private String URL_REQUEST;
    private ThaisMoodDB db = new ThaisMoodDB(AddMoodActivity.this);
    private SeekBar moodLevel;
    private EditText dateText;
    private TextView moodLevelBand, closeBtn;
    private ImageView violetEmo, greyEmo, greenEmo, yellowEmo, redEmo;
    private LinearLayout violetLayout, greyLayout, greenLayout, yellowLayout,
            redLayout, moodLevelSection, nextBtn;
    private ViewGroup transition;

    private int selectedMood = -1, moodLevelValue = 1;
    private Dialog helpDialog;
    private boolean isEdit = false;
    private int editID;
    private String editDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        Intent intent = getIntent();
        isEdit = intent.getExtras().getBoolean("isEdit");

        transition = findViewById(R.id.add_mood_transition);
        URL_REQUEST = getString(R.string.url_create_mood);
        closeBtn = findViewById(R.id.add_mood_help_btn);
        violetEmo = findViewById(R.id.add_mood_emo_violet_icon);
        greyEmo = findViewById(R.id.add_mood_emo_grey_icon);
        greenEmo = findViewById(R.id.add_mood_emo_green_icon);
        yellowEmo = findViewById(R.id.add_mood_emo_yellow_icon);
        redEmo = findViewById(R.id.add_mood_emo_red_icon);
        violetLayout = findViewById(R.id.add_mood_emo_violet_layout);
        greyLayout = findViewById(R.id.add_mood_emo_grey_layout);
        greenLayout = findViewById(R.id.add_mood_emo_green_layout);
        yellowLayout = findViewById(R.id.add_mood_emo_yellow_layout);
        redLayout = findViewById(R.id.add_mood_emo_red_layout);
        nextBtn = findViewById(R.id.add_mood_next_btn);
        moodLevel = findViewById(R.id.add_mood_level_seek_bar);
        dateText = findViewById(R.id.add_mood_date_select);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            moodLevel.setMin(1);
        }
        moodLevel.setMax(3);
        moodLevelBand = findViewById(R.id.add_mood_level_mood_level_label);
        moodLevelSection = findViewById(R.id.add_mood_seek_bar_section);

        setMoodLayout();
        if(isEdit){
            editID = intent.getExtras().getInt("id");
            selectedMood = intent.getExtras().getInt("selectedMood");
            moodLevelValue = intent.getExtras().getInt("level");
            editDate = intent.getExtras().getString("date");
            TransitionManager.beginDelayedTransition(transition);
            String[] dateToSet = editDate.split("/");
            int dayofmonth = Integer.parseInt(dateToSet[2]);
            int monthOfYear = Integer.parseInt(dateToSet[1]) -1;
            int year = Integer.parseInt(dateToSet[0]);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayofmonth);
            dateText.setText(dayofmonth + " " + MyCalender.getMonthOfYear(monthOfYear) + " " + year);
            setMoodActive();
        }
        setDatePicker();

    }

    private void setDatePicker() {
        String  todayText =  calendar.get(Calendar.DAY_OF_MONTH) + " " +
                MyCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) + " " +
                (calendar.get(Calendar.YEAR) + 543);
        dateText.setText(todayText);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMoodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        dateText.setText(dayOfMonth + " " + MyCalender.getMonthOfYear(month) + " " + (year + 543));

                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                if(isEdit){
                    Date date = new Date(editDate);
                    datePickerDialog.getDatePicker().setMaxDate(date.getTime());
                    datePickerDialog.getDatePicker().setMinDate(date.getTime());
                }else {
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                }

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

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog = new Dialog(AddMoodActivity.this);
                helpDialog.setContentView(R.layout.dialog_mood_details);
                LinearLayout close = helpDialog.findViewById(R.id.dialog_moood_des_close_btn);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        helpDialog.dismiss();
                    }
                });
                helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                helpDialog.show();
            }
        });

        violetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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
                if(isEdit){
                    if(db.updateMood(editID, selectedMood, moodLevelValue)){
                        sendEditMoodRequestToServer(selectedMood, moodLevelValue, editDate);
                        finish();
                    }else{
                        try {
                            throw new Exception("ERROR");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    if(!(selectedMood == -1)){
                        sendCreateMoodRequestToServer(selectedMood, moodLevelValue, getDateString());
                        if(db.insertMood(selectedMood, moodLevelValue, getDateString())){
                            finish();
                        }else{
                            try {
                                throw new Exception("ERROR");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
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

    private void setMoodActive(){
        nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        nextBtn.setEnabled(true);
        System.out.println("Mood: " + selectedMood);
        switch (selectedMood){
            case 1:
                violetEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_violet_fill));
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_violet));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_violet));
                moodLevelSection.setVisibility(View.VISIBLE);
                break;
            case 2:
                greyEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_grey_fill));
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_grey));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_grey));
                moodLevelSection.setVisibility(View.VISIBLE);
                break;
            case 3:
                greenEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_green_fill));
                break;
            case 4:
                yellowEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_yellow_fill));
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_yellow));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_yellow));
                moodLevelSection.setVisibility(View.VISIBLE);
                break;
            case 5:
                redEmo.setImageDrawable(getResources().getDrawable(R.drawable.emo_red_fill));
                moodLevelSection.setBackground(getResources().getDrawable(R.drawable.mood_box_red));
                moodLevel.setProgressDrawable(getResources().getDrawable(R.drawable.seek_bg_red));
                moodLevelSection.setVisibility(View.VISIBLE);
                break;
            default:
                return;
        }
    }

    private void sendCreateMoodRequestToServer(final int mood, final int level, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(AddMoodActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    Toast.makeText(AddMoodActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(AddMoodActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoodActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("mood", mood + "");
                MyData.put("level", level + "");
                MyData.put("date", date);
                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("authorization", db.getToken());
                return params;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void sendEditMoodRequestToServer(final int mood, final int level, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(AddMoodActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.PUT, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    Toast.makeText(AddMoodActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(AddMoodActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoodActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("mood", mood + "");
                MyData.put("level", level + "");
                MyData.put("date", date);
                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("authorization", db.getToken());
                return params;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
