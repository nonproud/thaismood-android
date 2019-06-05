package com.nnspace.thaismoodandroid;

import android.content.Context;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.List.MoodObject;

import java.util.ArrayList;

public class CheckDepress {

    private Context mContext;
    private ThaisMoodDB db;
    private ArrayList<MoodObject> moodList;
    private boolean isDepress = true, isDoEvaluation = false;

    public CheckDepress(Context context){
        mContext = context;
        db = new ThaisMoodDB(mContext);
        moodList = new ArrayList<>();
        moodList = db.getMoodDepressed();
        check();
    }

    private void check() {
        if(moodList.size() >= 7){
            for(int i = 0; i < moodList.size(); i++){
                MoodObject temp = moodList.get(i);
                if(temp.getMoodType().equals(MoodType.GREY) || temp.getMoodType().equals(MoodType.VIOLET)){
                    continue;
                }else {
                    isDepress = false;
                    break;
                }
            }

        }else{
            isDepress = false;
        }

        if(moodList.size() >= 14 && getIsDepress()){
            isDoEvaluation = true;
        }
    }

    public boolean getIsDepress(){
        return isDepress;
    }

    public boolean isDoEvaluation(){
        return isDoEvaluation;
    }

}
