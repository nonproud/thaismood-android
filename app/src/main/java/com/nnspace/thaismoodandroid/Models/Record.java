package com.nnspace.thaismoodandroid.Models;

import com.nnspace.thaismoodandroid.Models.MoodType;

public class Record {
    private MoodType type;

    public Record(MoodType type){
        this.type = type;
    }

    private int getColor(){
        return type.getColor();
    }

    private int getBox(){
        return type.getBox();
    }

    private String getMoodString(){
        return type.getMoodName();
    }
}
