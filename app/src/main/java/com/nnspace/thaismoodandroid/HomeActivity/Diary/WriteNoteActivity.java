package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.MyCalender;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WriteNoteActivity extends AppCompatActivity {

    private ThaisMoodDB db;
    private String URL_REQUEST;
    private TextView dateText;
    private EditText titleText, storyText;
    private FloatingActionButton saveBtn;
    private LinearLayout dateLayout;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog = null;
    private boolean isEdit = false;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        db = new ThaisMoodDB(WriteNoteActivity.this);
        URL_REQUEST = getResources().getString(R.string.url_diary);

        dateText = findViewById(R.id.diary_date);
        titleText = findViewById(R.id.diary_title);
        storyText = findViewById(R.id.diary_story);
        saveBtn = findViewById(R.id.diary_save_btn);
        dateLayout = findViewById(R.id.write_note_date_layout);

        Intent intent = getIntent();
        isEdit = intent.getExtras().getBoolean("isEditNote");
        if(isEdit){
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
                if(isEdit){
                    sendEditDiaryRequestToServer(myTitle, myStory, myDate);
                    if(db.updateNote(noteId, myTitle, myStory)){
                        finish();
                    }
                }else{
                    sendCreateDiaryRequestToServer(myTitle, myStory, myDate);
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
                if(isEdit){

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

    private void sendCreateDiaryRequestToServer(final String title, final String story, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(WriteNoteActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Create note respones -->: " + response);
                if(!response.toString().equals("0")){
                    Toast.makeText(WriteNoteActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(WriteNoteActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WriteNoteActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("title", title);
                MyData.put("story", story + "");
                MyData.put("date", date);
                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.println("token: " + db.getToken());
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

    private void sendEditDiaryRequestToServer(final String title, final String story, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(WriteNoteActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.PUT, URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Create note respones -->: " + response);
                if(!response.toString().equals("0")){
                    Toast.makeText(WriteNoteActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_LONG);
                }else if(response.toString().equals("0")){
                    Toast.makeText(WriteNoteActivity.this, "ไม่สามารถบันทึกข้อมูลไปที่ Sever ได้", Toast.LENGTH_LONG);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WriteNoteActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("title", title);
                MyData.put("story", story + "");
                MyData.put("date", date);
                return MyData;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.println("token: " + db.getToken());
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
