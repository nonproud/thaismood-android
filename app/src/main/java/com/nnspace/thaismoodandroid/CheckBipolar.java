package com.nnspace.thaismoodandroid;

import android.content.Context;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.List.MoodObject;

import java.util.ArrayList;

public class CheckBipolar {

    private Context mContext;
    private ThaisMoodDB db;
    private ArrayList<MoodObject> moodList;
    private boolean isBipolar = true, isDoEvaluation = false;

    public CheckBipolar(Context context){
        mContext = context;
        db = new ThaisMoodDB(mContext);
        moodList = new ArrayList<>();
        moodList = db.getMoodBipolar();
        check();
    }

    private void check() {
        if(moodList.size() > 3){
            for(int i = 0; i < moodList.size(); i++){
                MoodObject temp = moodList.get(i);
                if(temp.getMoodType().equals(MoodType.RED) || temp.getMoodType().equals(MoodType.YELLOW)){
                    continue;
                }else {
                    isBipolar = false;
                    break;
                }
            }

        }else{
            isBipolar = false;
        }

        if(moodList.size() >= 7 && getIsBipolar()){
            isDoEvaluation = true;
        }
    }

    public boolean getIsBipolar(){
        return isBipolar;
    }

    public boolean isDoEvaluation(){
        return isDoEvaluation;
    }

}
