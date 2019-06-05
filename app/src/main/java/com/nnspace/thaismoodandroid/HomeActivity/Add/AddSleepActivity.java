package com.nnspace.thaismoodandroid.HomeActivity.Add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.MyThaiCalender;
import com.nnspace.thaismoodandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSleepActivity extends AppCompatActivity {

    private TextView startTime, endTime, todayDate, totalTime;
    private LinearLayout dateSelect, startTimeSelect, endTimeSelect, saveBtn;
    private final Calendar calendar = Calendar.getInstance(), calendarEnd = Calendar.getInstance();
    private boolean isStartSet = false, isEndSet = false;
    private float totalSleep;
    String startTimeString, endTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sleep);
        startTime = findViewById(R.id.start_time);
        totalTime = findViewById(R.id.total_time);
        endTime = findViewById(R.id.end_time);
        todayDate = findViewById(R.id.add_sleep_date_text);
        dateSelect = findViewById(R.id.add_sleep_date_select);
        startTimeSelect = findViewById(R.id.add_sleep_start_time_select);
        endTimeSelect = findViewById(R.id.add_sleep_end_time_select);
        todayDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + " " + MyThaiCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) +
                " " + calendar.get(Calendar.YEAR));

        saveBtn = findViewById(R.id.add_sleep_save_btn);

        setDatePicker();
        setTimePicker();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThaisMoodDB db = new ThaisMoodDB(AddSleepActivity.this);
                if(db.insertSleep(totalSleep, startTimeString, endTimeString, getDateString())){
                    finish();
                }else {

                }
            }
        });
        
    }

    private void setTimePicker() {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MONTH, 0);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MONTH, 0);

        startTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String hr = "" + hourOfDay, min = "" + minute;
                        if(hourOfDay < 10){
                            hr = "0" + hourOfDay;
                        }
                        if(minute <10){
                            min = "0" + minute;
                        }
                        startTime.setText(hr + ":" + min);
                        startTimeString = hr + ":" + min;
                        isStartSet = true;
                        if(isStartSet && isEndSet){
                            setReadyToSave();
                        }

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        endTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendarEnd.set(Calendar.MINUTE, minute);
                        String hr = "" + hourOfDay, min = "" + minute;
                        if(hourOfDay < 10){
                            hr = "0" + hourOfDay;
                        }
                        if(minute <10){
                            min = "0" + minute;
                        }
                        endTime.setText(hr + ":" +  min);
                        endTimeString = hr + ":" + min;
                        isEndSet = true;
                        if(isStartSet && isEndSet){
                            setReadyToSave();
                        }
                    }
                }, calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE), true);
                timePickerDialog.show();

            }
        });

    }

    private void setDatePicker(){
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        todayDate.setText(dayOfMonth + " " + MyThaiCalender.getMonthOfYear(month) + " " + year);
                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        todayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        todayDate.setText(dayOfMonth + " " + MyThaiCalender.getMonthOfYear(month) + " " + year);

                    }
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private String getDateString(){
        Log.d("date", String.format("%d/%d/%d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        return String.format("%d/%d/%d",
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setReadyToSave(){

        saveBtn.setClickable(true);
        saveBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        totalTime.setText("รวมเวลานอน: " + getDifferentTime() + " ชั่วโมง");
        totalTime.setVisibility(View.VISIBLE);

    }

    private String getDifferentTime(){
        String time1 = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":00";
        String time2 = calendarEnd.get(Calendar.HOUR_OF_DAY) + ":" + calendarEnd.get(Calendar.MINUTE) + ":00";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        try {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            float diffTime = difference / (60 * 60 * 1000) % 24;
            if(diffTime < 0 ){
                diffTime = diffTime + 24;
            }
            totalSleep = diffTime;
            return String.format("%.0f", diffTime);
        }catch (Exception e){
            e.printStackTrace();
            return "err" ;
        }

    }
}
