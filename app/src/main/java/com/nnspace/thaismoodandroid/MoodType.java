package com.nnspace.thaismoodandroid;

public enum MoodType {
    RED("หงุดหงิด", R.color.mood_red, R.drawable.mood_box_red),
    YELLOW("คึกคัก", R.color.mood_yellow, R.drawable.mood_box_yellow),
    GREEN("ปกติ", R.color.mood_green, R.drawable.mood_box),
    VIOLET("เศร้า", R.color.mood_violet, R.drawable.mood_box_violet),
    GREY("เครียด", R.color.ios_grey, R.drawable.mood_box_grey);

    private int color, box;
    private String name;

    MoodType(String name, int color, int box){
        this.name = name;
        this.color = color;
        this.box = box;
    }

    public int getColor(){
        return this.color;
    }

    public int getBox(){
        return this.box;
    }

    public String getMoodName(){
        return name;
    }


}
