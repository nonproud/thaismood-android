package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.ActivityModel;
import com.nnspace.thaismoodandroid.DatabaseModel.EmotionModel;
import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.DatabaseModel.ExerciseModel;
import com.nnspace.thaismoodandroid.DatabaseModel.LogonModel;
import com.nnspace.thaismoodandroid.DatabaseModel.NoteModel;
import com.nnspace.thaismoodandroid.DatabaseModel.ProfileModel;
import com.nnspace.thaismoodandroid.DatabaseModel.SleepModel;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.DiaryObject;
import com.nnspace.thaismoodandroid.MoodObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThaisMoodDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;


    public ThaisMoodDB(Context context){
        super(context, "ThaisMoodDB", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ActivityModel.CREATE_TABLE_STRING);
        db.execSQL(EmotionModel.CREATE_TABLE_STRING);
        db.execSQL(EvaluationModel.CREATE_TABLE_STRING);
        db.execSQL(ExerciseModel.CREATE_TABLE_STRING);
        db.execSQL(LogonModel.CREATE_TABLE_STRING);
        db.execSQL(NoteModel.CREATE_TABLE_STRING);
        db.execSQL(ProfileModel.CREATE_TABLE_STRING);
        db.execSQL(SleepModel.CREATE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS ";
        db.execSQL(DROP_FRIEND_TABLE + ActivityModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + EmotionModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + EvaluationModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + ActivityModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + ExerciseModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + LogonModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + NoteModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + ProfileModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + SleepModel.TABLE_NAME);
        Log.d("sql", "OnUpgade was called.");
        onCreate(db);
    }

    public Map<String, String> isLogon(){
        String query_logon_tabel = String.format("SELECT * FROM %s", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        Map<String, String> data = null;
        if(result.moveToFirst()){
            data = new HashMap<String, String>();
            data.put("status", result.getString(0));
            data.put("user_id", result.getString(1));
            data.put("email", result.getString(2));
            data.put("token", result.getString(3));
        }
        return data;
    }

    public String getUserID(){
        String query_logon_tabel = String.format("SELECT * FROM %s WHERE 1", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            String id = result.getString(1);
            return id;
        }
        return null;
    }

    public boolean updateToken(String token){
        String query_change_verify_status = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.TOKEN, token);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_verify_status);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean insertLogonResgist(String id, String email){
        String query_user_id = String.format("INSERT INTO %s (%s, %s, %s) values('%s', '%s', '%s')",LogonModel.TABLE_NAME,
                LogonModel.ID, LogonModel.EMAIL, LogonModel.STATUS, id, email, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean updateVerifyStatus(int status){
        String query_change_verify_status = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.STATUS, status);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_verify_status);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean updateEmail(String email){
        String query_change_email = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.EMAIL, email);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_email);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean insertLogonLogin(String id, String email, String status, String token){
        String query_user_id = String.format("INSERT INTO %s (%s, %s, %s, %s) values(%s, '%s', '%s', '%s')", LogonModel.TABLE_NAME,
                LogonModel.STATUS, LogonModel.ID, LogonModel.EMAIL, LogonModel.TOKEN, id, email, status, token);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }
    
    public boolean insertMood(int mood, int level, String date){

        String query_insert_mood = "INSERT INTO " + EmotionModel.TABLE_NAME + " (" + EmotionModel.EMOTION +
                ", " + EmotionModel.LEVEL +
                ", " + EmotionModel.DATE + ") values(" + mood + ", " + level + ", '" + date + "');";

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
                EmotionModel.ID, id);
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
                EmotionModel.TABLE_NAME, EmotionModel.DATE);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_mood, null);
        ArrayList<MoodObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                int mood = result.getInt(1);
                int level = result.getInt(2);
                String date = result.getString(3);
                MoodObject moodObj = new MoodObject(id, mood, level, date);
                obj.add(moodObj);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }

    public ArrayList<MoodObject> getMoodRange(String fromDate, String toDate){

        String queryString = String.format("select * from %s where date >= '%s' and date <= '%s' ORDER BY %s ASC;",
                EmotionModel.TABLE_NAME, fromDate, toDate, EmotionModel.DATE);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(queryString, null);
        ArrayList<MoodObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                int mood = result.getInt(1);
                int level = result.getInt(2);
                String date = result.getString(3);
                MoodObject moodObj = new MoodObject(id, mood, level, date);
                obj.add(moodObj);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }

    public boolean insertNote(String title, String story, String date){

        String query_insert = String.format("INSERT INTO %s (%s, %s, %s) VALUES('%s', '%s', '%s');",
                NoteModel.TABLE_NAME, NoteModel.TITLE, NoteModel.NOTE, NoteModel.DATE,
                title, story, date);

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_insert);
        }catch (Exception err){
            err.printStackTrace();
            Log.d("sql", query_insert + " :FAILED" );
            System.out.println(query_insert + " :FAILED");
            return false;
        }
        Log.d("sql", query_insert + " :SUCCESSFULLY" );
        System.out.println(query_insert + " :SUCCESSFULLY");
        return true;
    }

    public ArrayList<DiaryObject> getAllNote(){
        String query_get_note = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC",
                NoteModel.TABLE_NAME, NoteModel.DATE);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_note, null);
        ArrayList<DiaryObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                String title = result.getString(1);
                String story = result.getString(2);
                String date = result.getString(3);
                DiaryObject diaryObject = new DiaryObject(1, title, story, date);
                obj.add(diaryObject);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        Log.d("diary", "All note size: " + obj.size());
        return obj;
    }
}
