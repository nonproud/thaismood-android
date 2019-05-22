package com.nnspace.thaismoodandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoodObject {
    private int id;
    private MoodType moodType;
    private int level;
    private Date dateCreated;
    private String dateFromDb;

    public MoodObject(int id, int moodType, int level, String date){
        this.id = id;
        dateCreated = new Date();
        dateFromDb = date;
        switch (moodType){
            case 1:
                this.moodType = MoodType.VIOLET;
                break;
            case 2:
                this.moodType = MoodType.GREY;
                break;
            case 3:
                this.moodType = MoodType.GREEN;
                break;
            case 4:
                this.moodType = MoodType.YELLOW;
                break;
            case 5:
                this.moodType = MoodType.RED;
                break;
        }
        this.level = level;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            this.dateCreated = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public MoodType getMoodType() {
        return moodType;
    }

    public void setMoodType(MoodType moodType) {
        this.moodType = moodType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateString(){
        return dateFromDb;
    }
}
