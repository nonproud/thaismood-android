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
import com.nnspace.thaismoodandroid.DatabaseModel.ProfileGeneralModel;
import com.nnspace.thaismoodandroid.DatabaseModel.ProfilePatientModel;
import com.nnspace.thaismoodandroid.DatabaseModel.SleepModel;
import com.nnspace.thaismoodandroid.EvaluationHistory.EvaluationObject;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.DiaryObject;
import com.nnspace.thaismoodandroid.HomeActivity.List.ActivityObject;
import com.nnspace.thaismoodandroid.HomeActivity.List.MoodObject;
import com.nnspace.thaismoodandroid.HomeActivity.List.RecordObject;
import com.nnspace.thaismoodandroid.HomeActivity.List.SleepObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThaisMoodDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context mContext;

    /***************************** CONSTRUCTOR ****************************/
    public ThaisMoodDB(Context context){
        super(context, "ThaisMoodDB", null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ActivityModel.CREATE_TABLE_STRING);
        db.execSQL(EmotionModel.CREATE_TABLE_STRING);
        db.execSQL(EvaluationModel.CREATE_TABLE_STRING);
        db.execSQL(ExerciseModel.CREATE_TABLE_STRING);
        db.execSQL(LogonModel.CREATE_TABLE_STRING);
        db.execSQL(NoteModel.CREATE_TABLE_STRING);
        db.execSQL(ProfileGeneralModel.CREATE_TABLE_STRING);
        db.execSQL(ProfilePatientModel.CREATE_TABLE_STRING);
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
        db.execSQL(DROP_FRIEND_TABLE + ProfileGeneralModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + ProfilePatientModel.TABLE_NAME);
        db.execSQL(DROP_FRIEND_TABLE + SleepModel.TABLE_NAME);
        Log.d("sql", "OnUpgade was called.");
        onCreate(db);
    }
    /*************************** CONSTRUCTOR ****************************/

    /*************************** LOGON ****************************/
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

    public String getUsername(){
        String query_logon_tabel = String.format("SELECT username FROM %s WHERE 1", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            String username = result.getString(0);
            return username;
        }
        return null;
    }

    public String getEmail(){
        String query_logon_tabel = String.format("SELECT email FROM %s WHERE 1", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            String email = result.getString(0);
            return email;
        }
        return null;
    }

    public char getType() {
        String query_logon_tabel = String.format("SELECT %s FROM %s WHERE 1", LogonModel.TYPE, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            char type = result.getString(0).charAt(0);
            return type;
        }
        return 'g';
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

    public boolean insertLogonResgist(String username, String email){
        String query_user_id = String.format("INSERT INTO %s (%s, %s, %s) values('%s', '%s', '%s')",LogonModel.TABLE_NAME,
                LogonModel.USERNAME, LogonModel.EMAIL, LogonModel.STATUS, username, email, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean updateVerifyStatus(){
        String query_change_verify_status = String.format("UPDATE login SET %s = 1 WHERE 1'", LogonModel.STATUS);
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

    public boolean insertLogonLogin(String username, String email, String status, String token){
        String query_user_id = String.format("INSERT INTO %s(%s, %s, %s, %s) values(%s, '%s', '%s', '%s')", LogonModel.TABLE_NAME,
                LogonModel.STATUS, LogonModel.USERNAME, LogonModel.EMAIL, LogonModel.TOKEN, status, username, email, token);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }
    /*************************** LOGON ****************************/


    /************************* PROFILE ****************************/

    public Map<String, String> getProfileGeneralDetails(){
        Map<String, String> data = new HashMap<>();
        String sql = String.format("SELECT * FROM %s WHERE 1;", ProfileGeneralModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            String dob = result.getString(1);
            String isCaffeine = result.getString(2);
            String isDrug = result.getString(3);
            String created = result.getString(4);
            String modified = result.getString(5);
            data.put("dob", dob);
            data.put("isCaffeine", isCaffeine);
            data.put("isDrug", isDrug);
            data.put("created", created);
            data.put("modified", modified);
            return data;
        }

        return null;
    }

    public Map<String, String> getProfilePateintDetails(){
        Map<String, String> data = new HashMap<>();
        String sql = String.format("SELECT * FROM %s WHERE 1;", ProfilePatientModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            String dob = result.getString(1);
            String isCaffeine = result.getString(2);
            String isDrug = result.getString(3);
            String sex = result.getString(4);
            String isPregnant = result.getString(5);
            Float weight = result.getFloat(6);
            Float height = result.getFloat(7);
            Float bmi = result.getFloat(8);
            String disease = result.getString(9);
            String created = result.getString(10);
            String modified = result.getString(11);
            data.put("dob", dob);
            data.put("isCaffeine", isCaffeine);
            data.put("isDrug", isDrug);
            data.put("sex", sex);
            data.put("isPregnant", isPregnant);
            data.put("weight", weight + "");
            data.put("height", height + "");
            data.put("bmi", bmi + "");
            data.put("disease", disease);
            data.put("created", created);
            data.put("modified", modified);
            return data;
        }

        return null;
    }

    /************************* PROFILE ****************************/


    /*************************** RECORD ****************************/
    public ArrayList<RecordObject> getAllRecordObject(){
        String query_get_mood = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                        "FROM %s " +
                        "LEFT JOIN %s ON %s.%s = %s.%s " +
                        "LEFT JOIN %s ON %s.%s = %s.%s " +
                        "ORDER BY %s.%s DESC;",
                EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                EmotionModel.TABLE_NAME,
                SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                EmotionModel.TABLE_NAME, EmotionModel.DATE);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_mood, null);
        ArrayList<RecordObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int moodId = result.getInt(0);
                int moodType = result.getInt(1);
                int moodLevel = result.getInt(2);
                int sleepId = result.getInt(3);
                float totalSleep = result.getFloat(4);
                String startTime = result.getString(5);
                String endTime = result.getString(6);
                int activityId = result.getInt(7);
                String activity = result.getString(8);
                String date = result.getString(9);

                MoodObject  moodObject = new MoodObject(moodId, moodType, moodLevel, date);
                SleepObject sleepObject = new SleepObject(sleepId, totalSleep, startTime, endTime, date);
                ActivityObject activityObject = new ActivityObject(activityId, activity, date);
                RecordObject recordObject = new RecordObject(moodObject, sleepObject, activityObject);
                obj.add(recordObject);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }
    /*************************** RECORD ****************************/

    /*************************** MOOD ****************************/
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

    public boolean updateMood(int id, int mood, int level){

        String query_insert_mood = String.format("UPDATE %s set %s = %d, %s = %d WHERE %s = %s;",
                EmotionModel.TABLE_NAME, EmotionModel.EMOTION, mood, EmotionModel.LEVEL, level, EmotionModel.ID, id); ;
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

    public ArrayList<MoodObject> getMoodYear(String year){

//        String queryString = "select * from " +  EmotionModel.TABLE_NAME + " AS s WHERE strftime('%Y', s.date) = '" +
//        year + "' ORDER BY " + EmotionModel.DATE + " ASC;";
        String queryString = "select * from " + EmotionModel.TABLE_NAME + " where 1;";

        Log.d("SQL", queryString);

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

    public ArrayList<MoodObject> getMoodDepressed(){

        String queryString = String.format("select * from %s where 1 ORDER BY %s desc limit 15;",
                EmotionModel.TABLE_NAME,  EmotionModel.DATE);

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

    public ArrayList<MoodObject> getMoodBipolar(){

        String queryString = String.format("select * from %s where 1 ORDER BY %s desc limit 8;",
                EmotionModel.TABLE_NAME,  EmotionModel.DATE);

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

    public int getMoodCount(){
        String sql = String.format("SELECT count(*) FROM %s;", EmotionModel.TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        return result.getInt(0);

    }
    /*************************** MOOD ****************************/

    /*************************** DIARY ****************************/
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

    public boolean updateNote(int id, String title, String story) {

        String sql = String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = %d;",
                NoteModel.TABLE_NAME, NoteModel.TITLE, title,  NoteModel.NOTE, story, NoteModel.ID, id);

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            Log.d("sql", sql + " :FAILED" );
            System.out.println(sql + " :FAILED");
            return false;
        }
        Log.d("sql", sql + " :SUCCESSFULLY" );
        return true;
    }

    public ArrayList<DiaryObject> getAllNote(){
        String query_get_note = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s FROM %s LEFT JOIN %s ON %s.%s = %s.%s ORDER BY %s.%s DESC;",
                NoteModel.TABLE_NAME, NoteModel.ID, NoteModel.TABLE_NAME, NoteModel.TITLE, NoteModel.TABLE_NAME, NoteModel.NOTE,
                EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL, NoteModel.TABLE_NAME, NoteModel.DATE,
                NoteModel.TABLE_NAME, EmotionModel.TABLE_NAME,
                NoteModel.TABLE_NAME, NoteModel.DATE, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                NoteModel.TABLE_NAME, NoteModel.DATE);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_note, null);
        ArrayList<DiaryObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                String title = result.getString(1);
                String story = result.getString(2);
                int mood = result.getInt(3);
                int level = result.getInt(4);
                String date = result.getString(5);
                MoodObject moodObject = new MoodObject(mood, level, date);
                DiaryObject diaryObject = new DiaryObject(id, title, story, date, moodObject);
                obj.add(diaryObject);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        Log.d("diary", "All note size: " + obj.size());
        return obj;
    }

    public int getDiaryCount(){
        String sql = String.format("SELECT count(*) FROM %s;", NoteModel.TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        return result.getInt(0);
    }
    /*************************** DIARY ****************************/

    /*************************** EVALUATION ****************************/
    public boolean insertEvaluationScore(int score, String type, String date){

        String sql = String.format("UPDATE %s SET %s = %s WHERE %s = %s;",
                EvaluationModel.TABLE_NAME, type, score, EvaluationModel.date, date);

        if(type.equals(EvaluationModel._2q)){
            sql = String.format("INSERT INTO %s(%s, %s) VALUES('%s', '%s');",
                    EvaluationModel.TABLE_NAME, EvaluationModel._2q, EvaluationModel.date,
                    score, date);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            return false;
        }
        System.out.println(sql);
        return true;
    }

    public ArrayList<EvaluationObject> getAllEvaluation(){
        String query_get_note = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC",
                EvaluationModel.TABLE_NAME, EvaluationModel.date);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_note, null);
        ArrayList<EvaluationObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int score2q = result.getInt(0);
                int score9q = result.getInt(1);
                int score8q = result.getInt(2);
                int scoremdq = result.getInt(3);
                String date = result.getString(4);
                EvaluationObject evaluationObject = new EvaluationObject(mContext, score2q, score9q, score8q, scoremdq, date);
                obj.add(evaluationObject);
                System.out.println(String.format("%d %d %d %d", score2q, score9q, score8q, scoremdq));
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        Log.d("diary", "All note size: " + obj.size());
        return obj;
    }
    /*************************** EVALUATION ****************************/

    /*************************** SLEEP ****************************/
    public boolean insertSleep(float total, String start, String end, String date){

        String query_insert = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES(%s, '%s', '%s', '%s');",
                SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.START, SleepModel.END, SleepModel.DATE,
                total, start, end, date);

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

    public ArrayList<SleepObject> getSleep() {
        String query_get_mood = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC;",
                SleepModel.TABLE_NAME, SleepModel.DATE);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_mood, null);
        ArrayList<SleepObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                float total = result.getFloat(1);
                String start = result.getString(2);
                String end = result.getString(3);
                String date = result.getString(4);
                SleepObject moodObj = new SleepObject(id, total, start, end, date);
                obj.add(moodObj);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }

    public int getSleepCount(){
        String sql = String.format("SELECT count(*) FROM %s;", SleepModel.TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        return result.getInt(0);
    }



    /*************************** SLEEP ****************************/
}
