package com.nnspace.thaismoodandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ThaisMoodDB db;
    private String type;
    private Map<String, String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = new ThaisMoodDB(ProfileActivity.this);

        try{
            type = db.getType();
            if(type.equals("p")){
                data = db.getProfilePatientDetails();
                LinearLayout patientSection = findViewById(R.id.patient_section);
                patientSection.setVisibility(View.VISIBLE);
                setPatientProfile();
            }else{
                data = db.getProfileGeneralDetails();
                LinearLayout patientSection = findViewById(R.id.patient_section);
                patientSection.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        ImageView backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView username = findViewById(R.id.username);
        username.setText(db.getUsername());

        setCountBand();
        setProfile();
    }

    private void setCountBand() {
        TextView moodCount = findViewById(R.id.mood_count);
        TextView sleepCount = findViewById(R.id.sleep_count);
        TextView diaryCount = findViewById(R.id.diary_count);
        moodCount.setText("" + db.getMoodCount());
        sleepCount.setText("" + db.getSleepCount());
        diaryCount.setText("" + db.getDiaryCount());
    }

    private void setProfile() {
        TextView nickname = findViewById(R.id.nickname);
        TextView emergencyContact = findViewById(R.id.emergency_contact);
        TextView email = findViewById(R.id.email);
        TextView dob = findViewById(R.id.dob);
        TextView caffeine = findViewById(R.id.cafeine);
        TextView drug = findViewById(R.id.addict);

        nickname.setText(data.get("nickname"));
        emergencyContact.setText(data.get("emergency"));
        email.setText(db.getEmail());
        String[] dobTemp;
        String newDob = "";
        try{
            dobTemp = data.get("dob").split("/");
            newDob = String.format("%s/%s/%s", dobTemp[2], dobTemp[1], dobTemp[0]);
        }catch (Exception e){

            try{
                dobTemp = data.get("dob").split("-");
                newDob = String.format("%s/%s/%s", dobTemp[2], dobTemp[1], dobTemp[0]);

            }catch (Exception ex){
                newDob = data.get("dob");
            }
        }


        dob.setText(newDob);
        if (data.get("isCaffeine") == "1") {
            caffeine.setText("ใช้");
        }else{
            caffeine.setText("ไม่ใช้");
        }

        if (data.get("isDrug") == "1") {
            drug.setText("ใช้");
        }else{
            drug.setText("ไม่ใช้");
        }
    }

    private void setPatientProfile() {
        ImageView sex = findViewById(R.id.sex_icon);
        if(data.get("sex") == "male"){
            LinearLayout pregnantSection = findViewById(R.id.pregnant_section);
            pregnantSection.setVisibility(View.GONE);
            sex.setImageDrawable(getDrawable(R.drawable.im_male_foreground));

        }else{
            LinearLayout pregnantSection = findViewById(R.id.pregnant_section);
            pregnantSection.setVisibility(View.VISIBLE);
            boolean isPregnant = data.get("isPregnant") == "0" ? true:false;
            sex.setImageDrawable(getDrawable(R.drawable.im_female_foreground));
            TextView pregnantText  = findViewById(R.id.pregnant_text);
            String pregnant = isPregnant ? "ตั้งครรภ์":"ไม่ตั้งครรภ์";
            pregnantText.setText(pregnant);
        }

        TextView weight = findViewById(R.id.weight_text);
        weight.setText(data.get("weight"));
        TextView height =findViewById(R.id.height_text);
        height.setText(data.get("height"));
        TextView bmi = findViewById(R.id.bmi_text);
        bmi.setText(data.get("bmi"));

    }

}
