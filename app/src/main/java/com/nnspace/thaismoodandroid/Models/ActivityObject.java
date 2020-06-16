package com.nnspace.thaismoodandroid.Models;

public class ActivityObject {

    int id;
    private String activity, date;

    public ActivityObject(int id, String activity, String date){
        this.activity = activity;
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getActivity() {
        return activity;
    }

    public String getDate() {
        return date;
    }
}
