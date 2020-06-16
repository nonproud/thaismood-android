package com.nnspace.thaismoodandroid.Models;

import com.nnspace.thaismoodandroid.R;

public enum MoodType {
    RED(5, "หงุดหงิด", R.color.mood_red, R.drawable.mood_box_red, R.drawable.emo_red_fill,
            R.drawable.black_levels1, R.drawable.black_levels2, R.drawable.black_levels3),
    YELLOW(4, "คึกคัก", R.color.mood_yellow, R.drawable.mood_box_yellow, R.drawable.emo_yellow_fill,
            R.drawable.black_levels1, R.drawable.black_levels2, R.drawable.black_levels3),
    GREEN(3, "ปกติ", R.color.mood_green, R.drawable.mood_box, R.drawable.emo_green_fill,
            R.drawable.black_levels0, 0, 0),
    VIOLET(1, "เศร้า", R.color.mood_violet, R.drawable.mood_box_violet, R.drawable.emo_violet_fill,
            R.drawable.black_levels1, R.drawable.black_levels2, R.drawable.black_levels3),
    GREY(2, "เครียด", R.color.ios_grey, R.drawable.mood_box_grey, R.drawable.emo_grey_fill,
            R.drawable.black_levels1, R.drawable.black_levels2, R.drawable.black_levels3);

    private int color;
    private int box;
    private int icon;
    private int l1;
    private int l2;
    private int l3;

    public int getNumber() {
        return number;
    }

    private int number;
    private String name;

    MoodType(int number, String name, int color, int box, int icon, int l1, int l2, int l3){
        this.name = name;
        this.color = color;
        this.box = box;
        this.icon = icon;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.number = number;
    }

    public int getColor(){
        return this.color;
    }

    public int getBox(){
        return this.box;
    }

    public int getIcon(){
        return this.icon;
    }

    public String getMoodName(){
        return name;
    }

    public int getMoodLevelIcon(int level){

        switch (level){
            case 1:
                return l1;
            case 2:
                return l2;
            case 3:
                return l3;
        }
        return l1;
    }

}
