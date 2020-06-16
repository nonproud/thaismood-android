package com.nnspace.thaismoodandroid.Models;

public class SleepObject {
    int id;
    private float toalTime;
    private String start, end, date;

    public SleepObject(int id, float toalTime, String start, String end, String date){
        this.id = id;
        this.toalTime = toalTime;
        this.start = start;
        this.end = end;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public float getToalTime() {
        return toalTime;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDate() {
        return date;
    }
}
