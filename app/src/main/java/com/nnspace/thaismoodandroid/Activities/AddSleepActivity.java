package com.nnspace.thaismoodandroid.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSleepActivity extends AppCompatActivity {

    private String URL_REQUEST;
    private ThaisMoodDB db;
    private TextView startTime, endTime, todayDate, totalTime;
    private LinearLayout dateSelect, startTimeSelect, endTimeSelect, saveBtn;
    private final Calendar calendar = Calendar.getInstance(), calendarEnd = Calendar.getInstance();
    private boolean isStartSet = false, isEndSet = false;
    private float totalSleep;
    String startTimeString, endTimeString;
    private boolean isEdit = false;
    private int editId;
    private String startEdit, editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URL_REQUEST = getResources().getString(R.string.url_sleep);
        db = new ThaisMoodDB(AddSleepActivity.this);
        Intent intent = getIntent();
        isEdit = intent.getExtras().getBoolean("isEdit");

        setContentView(R.layout.activity_add_sleep);
        startTime = findViewById(R.id.start_time);
        totalTime = findViewById(R.id.total_time);
        endTime = findViewById(R.id.end_time);
        todayDate = findViewById(R.id.add_sleep_date_text);
        dateSelect = findViewById(R.id.add_sleep_date_select);
        startTimeSelect = findViewById(R.id.add_sleep_start_time_select);
        endTimeSelect = findViewById(R.id.add_sleep_end_time_select);
        if(isEdit){
            editId = intent.getExtras().getInt("id");
            editDate = intent.getExtras().getString("date");
            String[] tempDate = editDate.split("/");
            calendar.set(Calendar.YEAR, Integer.parseInt(tempDate[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(tempDate[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tempDate[2]));
        }
        todayDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + " " + MyCalender.getMonthOfYear(calendar.get(Calendar.MONTH)) +
                " " + calendar.get(Calendar.YEAR));

        saveBtn = findViewById(R.id.add_sleep_save_btn);

        setDatePicker();
        setTimePicker();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEdit){
                    sendEditSleepRequestToServer(totalSleep, startTimeString, endTimeString, editDate);
                    if(db.updateSleep(editId, totalSleep, startTimeString, endTimeString)){
                        finish();
                    }else {

                    }
                }else{
                    sendCreateSleepRequestToServer(totalSleep, startTimeString, endTimeString, getDateString());
                    if(db.insertSleep(totalSleep, startTimeString, endTimeString, getDateString())){
                        finish();
                    }else {

                    }
                }


            }
        });
        
    }

    private void setTimePicker() {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);

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
                        todayDate.setText(dayOfMonth + " " + MyCalender.getMonthOfYear(month) + " " + year);
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

        todayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        todayDate.setText(dayOfMonth + " " + MyCalender.getMonthOfYear(month) + " " + year);

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

    private void sendCreateSleepRequestToServer(final float total, final String start, final String end, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(AddSleepActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    Toast.makeText(AddSleepActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(AddSleepActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSleepActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("total", total + "");
                MyData.put("start", start + "");
                MyData.put("end", end + "");
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

    private void sendEditSleepRequestToServer(final float total, final String start, final String end, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(AddSleepActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.PUT, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.toString().equals("0")){
                    Toast.makeText(AddSleepActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(AddSleepActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSleepActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("total", total + "");
                MyData.put("start", start + "");
                MyData.put("end", end + "");
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
