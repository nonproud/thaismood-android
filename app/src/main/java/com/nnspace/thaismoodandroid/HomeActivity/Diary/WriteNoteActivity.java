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
import com.nnspace.thaismoodandroid.MainmenuTest;
import com.nnspace.thaismoodandroid.MyThaiCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class WriteNoteActivity extends AppCompatActivity {

    private TextView dateText;
    private EditText title, story;
    private FloatingActionButton saveBtn;
    private LinearLayout dateLayout;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        dateText = findViewById(R.id.diary_date);
        title = findViewById(R.id.diary_title);
        story = findViewById(R.id.diary_story);
        saveBtn = findViewById(R.id.diary_save_btn);
        dateLayout = findViewById(R.id.write_note_date_layout);

        Intent intent = getIntent();
        boolean isCustomDate = intent.getExtras().getBoolean("isCustom");
        boolean isHasStory = intent.getExtras().getBoolean("isHasStory");

        if(isCustomDate){
            int dayOfMonth = intent.getExtras().getInt("dom");
            int monthOfYear = intent.getExtras().getInt("moy");
            int year = intent.getExtras().getInt("y");

            calendar.set(year, monthOfYear, dayOfMonth);
        }

        if(isHasStory){
            String customTitle = intent.getExtras().getString("title");
            String customStory = intent.getExtras().getString("story");
            title.setText(customTitle);
            story.setText(customStory);
        }

        dateText.setText(getThaidateString());



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTitle = title.getText().toString();
                String myStory = story.getText().toString();
                String myDate = getDateString();
                ThaisMoodDB db = new ThaisMoodDB(WriteNoteActivity.this);
                if(db.insertNote(myTitle, myStory, myDate)){
                    finish();
                }else{
                    startActivity(new Intent(WriteNoteActivity.this, MainmenuTest.class));

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
                datePickerDialog.show();
            }
        });
    }

    private String getThaidateString(){
        return String.format("วัน%sที่ %d %s พ.ศ. %d", MyThaiCalender.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)),
                calendar.get(Calendar.DAY_OF_MONTH), MyThaiCalender.getMonthOfYear(calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.YEAR)  + 543);
    }

    private String getDateString(){
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
