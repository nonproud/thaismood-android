package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.EmotionModel;
import com.nnspace.thaismoodandroid.MoodObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class EmotionDB extends SQLiteOpenHelper {

    public EmotionDB(Context context){
        super(context, "ThaisMoodDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s int PRIMARY KEY, " +
                        "%s int NOT NULL, " +
                        "%s int NOT NULL, " +
                        "%s DATE NOT NULL " +
                        ");", EmotionModel.TABLE_NAME,
                EmotionModel.column.ID,
                EmotionModel.column.EMOTION,
                EmotionModel.column.LEVEL,
                EmotionModel.column.DATE
        );
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + EmotionModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }

    public boolean insertMood(int mood, int level, String date){

        String query_insert_mood = "INSERT INTO " + EmotionModel.TABLE_NAME + " (" + EmotionModel.column.EMOTION +
                ", " + EmotionModel.column.LEVEL +
                ", " + EmotionModel.column.DATE + ") values(" + mood + ", " + level + ", '" + date + "');";

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_insert_mood);
        }catch (Exception err){
            err.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteMood(int id){
        String query_delete_mood = String.format("DELETE FROM %s WHERE %s = %d",
                EmotionModel.TABLE_NAME,
                EmotionModel.column.ID, id);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_delete_mood);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public ArrayList<MoodObject> getAllMood(){
        String query_get_mood = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC;",
                EmotionModel.TABLE_NAME, EmotionModel.column.DATE);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_mood, null);
        ArrayList<MoodObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                int mood = result.getInt(1);
                int level = result.getInt(2);
                String note = result.getString(3);
                String date = result.getString(4);
                MoodObject moodObj = new MoodObject(id, mood, level, note, date);
                obj.add(moodObj);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }

    public ArrayList<MoodObject> getMoodWeek(String fromDate, String toDate){

        String queryString = String.format("select * from %s where date >= '%s' and date <= '%s' ORDER BY %s ASC;",
            EmotionModel.TABLE_NAME, fromDate, toDate, EmotionModel.column.DATE);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(queryString, null);
        ArrayList<MoodObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                int mood = result.getInt(1);
                int level = result.getInt(2);
                String note = result.getString(3);
                String date = result.getString(4);
                MoodObject moodObj = new MoodObject(id, mood, level, note, date);
                obj.add(moodObj);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }
}
