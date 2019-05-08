package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.EmotionModel;
import com.nnspace.thaismoodandroid.MoodObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EmotionDB extends SQLiteOpenHelper {

    public EmotionDB(Context context){
        super(context, "ThaisMoodDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMOTION_TABLE = String.format("CREATE TABLE %s (" +
                        "%s int NOT NULL, " +
                        "%s int NOT NULL, " +
                        "%s int NOT NULL, " +
                        "%s DATETIME NOT NULL, " +
                        "PRIMARY KEY (%s)" +
                        ");", EmotionModel.TABLE_NAME,
                EmotionModel.column.ID,
                EmotionModel.column.EMOTION,
                EmotionModel.column.LEVEL,
                EmotionModel.column.DATE,
                EmotionModel.column.ID
        );
        db.execSQL(CREATE_EMOTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + EmotionModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }

    public boolean insertMood(int mood, int level, String note){
        String query_insert_mood = String.format("INSERT INTO %s (%s, %s, %s, %s) values('%s', '%s', '%s', CONVERT(VARCHAR(10), getdate(), 103));",
                EmotionModel.TABLE_NAME,
                EmotionModel.column.EMOTION,
                EmotionModel.column.LEVEL,
                EmotionModel.column.NOTE,
                EmotionModel.column.DATE,
                mood, level, note);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_insert_mood);
        }catch (Exception err){
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

    public ArrayList<MoodObject> getMood(){
        String query_get_mood = String.format("SELECT * FROM %s WHERE 1;",
                EmotionModel.TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_mood, null);
        ArrayList<MoodObject> obj = new ArrayList<>();

        while (!result.isAfterLast()){
            MoodObject mood = new MoodObject()
        }
        if(result.moveToFirst()){
            data = new HashMap<String, String>();
            data.put("status", result.getString(0));
            data.put("user_id", result.getString(1));
            data.put("email", result.getString(2));
            data.put("token", result.getString(3));
        }

        return data;

    }
}
