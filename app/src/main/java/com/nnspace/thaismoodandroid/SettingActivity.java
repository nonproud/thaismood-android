package com.nnspace.thaismoodandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.rm.rmswitch.RMSwitch;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    public static final int MOOD_NOTIFICATION_REQUEST_CODE = 100;
    public final int SLEEP_NOTIFICATION_REQUEST_CODE = 200;
    private static final Calendar calendarMood = Calendar.getInstance();
    private static final Calendar calendarSleep = Calendar.getInstance();

    private RMSwitch moodNotiSwitch, sleepNotiSwitch;
    private TextView moodTimeText, sleepTimeText;
    private RelativeLayout moodTime, sleepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        moodNotiSwitch = findViewById(R.id.switch_mood_noti);
        sleepNotiSwitch = findViewById(R.id.switch_sleep_noti);

        moodTimeText = findViewById(R.id.noti_mood_time_text);
        sleepTimeText = findViewById(R.id.noti_sleep_time_text);

        moodTime = findViewById(R.id.noti_mood_time);
        sleepTime = findViewById(R.id.noti_sleep_time);

        moodNotiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RMSwitch rmSwitch = (RMSwitch) v;
                if(rmSwitch.isChecked()){
                    rmSwitch.setChecked(false);
                    moodTime.setVisibility(View.GONE);
                }else{
                    rmSwitch.setChecked(true);
                    moodTime.setVisibility(View.VISIBLE);

                }
            }
        });

        moodTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendarMood.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendarMood.set(Calendar.MONTH, minute);
                        moodTimeText.setText(hourOfDay + ":" + minute);
                        setUpMoodNotification();
                    }
                }, calendarMood.get(Calendar.HOUR_OF_DAY), calendarMood.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        sleepNotiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RMSwitch rmSwitch = (RMSwitch) v;
                if(rmSwitch.isChecked()){
                    rmSwitch.setChecked(false);
                    sleepTime.setVisibility(View.GONE);
                }else{
                    rmSwitch.setChecked(true);
                    sleepTime.setVisibility(View.VISIBLE);

                }
            }
        });



    }

    public void setUpMoodNotification(){

        Intent intent = new Intent(SettingActivity.this, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), MOOD_NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , calendarMood.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

}
