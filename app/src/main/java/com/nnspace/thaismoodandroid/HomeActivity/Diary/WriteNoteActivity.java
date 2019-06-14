package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.MyCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;
import java.util.Date;

public class WriteNoteActivity extends AppCompatActivity {

    private TextView dateText;
    private EditText titleText, storyText;
    private FloatingActionButton saveBtn;
    private LinearLayout dateLayout;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog = null;
    private boolean isEditNote = false;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        dateText = findViewById(R.id.diary_date);
        titleText = findViewById(R.id.diary_title);
        storyText = findViewById(R.id.diary_story);
        saveBtn = findViewById(R.id.diary_save_btn);
        dateLayout = findViewById(R.id.write_note_date_layout);

        Intent intent = getIntent();
        isEditNote = intent.getExtras().getBoolean("isEditNote");
        if(isEditNote){
            String title = intent.getExtras().getString("title");
            String story = intent.getExtras().getString("story");
            String date = intent.getExtras().getString("date");
            noteId = intent.getExtras().getInt("id");
            titleText.setText(title);
            storyText.setText(story);
            String[] dateToSet = date.split("/");
            calendar.set(Calendar.YEAR, Integer.parseInt(dateToSet[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateToSet[1]) -1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateToSet[2]));
        }

        dateText.setText(getThaidateString());



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTitle = titleText.getText().toString();
                String myStory = storyText.getText().toString();
                String myDate = getDateString();
                ThaisMoodDB db = new ThaisMoodDB(WriteNoteActivity.this);
                if(isEditNote){
                    if(db.updateNote(noteId, myTitle, myStory)){
                        finish();
                    }
                }else{
                    if(db.insertNote(myTitle, myStory, myDate)){
                        finish();
                    }
                }

            }
        });

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePickerDialog == null){
                    datePickerDialog = new DatePickerDialog(WriteNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            dateText.setText(getThaidateString());

                        }
                    }, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                }
                if(isEditNote){

                }else{
                    datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                }
                datePickerDialog.show();
            }
        });
    }

    private String getThaidateString(){
        return String.format("วัน%sที่ %d %s พ.ศ. %d", MyCalender.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)),
                calendar.get(Calendar.DAY_OF_MONTH), MyCalender.getMonthOfYear(calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.YEAR)  + 543);
    }

    private String getDateString(){
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
