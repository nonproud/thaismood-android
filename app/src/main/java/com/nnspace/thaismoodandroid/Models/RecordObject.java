package com.nnspace.thaismoodandroid.Models;

public class RecordObject {
    private MoodObject mood;
    private SleepObject sleep;
    private ActivityObject activityObject;

    public RecordObject(MoodObject mood, SleepObject sleep, ActivityObject activityObject){
        this.mood = mood;
        this.sleep = sleep;
        this.activityObject = activityObject;
    }

    public MoodObject getMood() {
        return mood;
    }

    public SleepObject getSleep() {
        return sleep;
    }

    public ActivityObject getActivityObject() {
        return activityObject;
    }
}
