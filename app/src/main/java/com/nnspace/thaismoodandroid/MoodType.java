package com.nnspace.thaismoodandroid;

public enum MoodType {
    RED("หงุดหงิด", R.color.mood_red, R.drawable.mood_box_red, R.drawable.emo_red_fill,
            R.drawable.one_red, R.drawable.two_red, R.drawable.three_red),
    YELLOW("คึกคัก", R.color.mood_yellow, R.drawable.mood_box_yellow, R.drawable.emo_yellow_fill,
            R.drawable.one_yellow, R.drawable.two_yellow, R.drawable.three_yellow),
    GREEN("ปกติ", R.color.mood_green, R.drawable.mood_box, R.drawable.emo_green_fill,
            R.drawable.zero_green, 0, 0),
    VIOLET("เศร้า", R.color.mood_violet, R.drawable.mood_box_violet, R.drawable.emo_violet_fill,
            R.drawable.one_violet, R.drawable.two_violet, R.drawable.three_violer),
    GREY("เครียด", R.color.ios_grey, R.drawable.mood_box_grey, R.drawable.emo_grey_fill,
            R.drawable.one_grey, R.drawable.two_grey, R.drawable.three_grey);

    private int color, box, icon, l1, l2, l3;
    private String name;

    MoodType(String name, int color, int box, int icon, int l1, int l2, int l3){
        this.name = name;
        this.color = color;
        this.box = box;
        this.icon = icon;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
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
