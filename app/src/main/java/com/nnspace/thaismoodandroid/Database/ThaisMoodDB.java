package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public boolean isLogon(){
        String query_logon_tabel = String.format("SELECT * FROM %s", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        
        if(result.moveToFirst()){
            db.close();
            result.close();
            return true;
        }
        result.close();
        db.close();
        return false;
    }

    public String getUsername(){
        String query_logon_tabel = String.format("SELECT %s FROM %s WHERE 1", LogonModel.USERNAME, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            String username = result.getString(0);
            result.close();
            db.close();
            return username;
        }
        result.close();
        db.close();
        return null;
    }

    public String getEmail(){
        String query_logon_tabel = String.format("SELECT %s FROM %s WHERE 1", LogonModel.EMAIL, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.moveToFirst()){
            String email = result.getString(0);
            result.close();
            db.close();
            return email;
        }
        result.close();
        db.close();
        return null;
    }

    public String getType() {
        String query_logon_tabel = String.format("SELECT %s FROM %s WHERE 1", LogonModel.TYPE, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        String type = "";
        if(result.moveToFirst()){
            type = result.getString(0);
        }
        result.close();
        db.close();
        return type;
    }

    public String getToken() {
        String sql = String.format("SELECT %s FROM %s WHERE 1", LogonModel.TOKEN, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        String token = "";
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            token = result.getString(0);
        }
        return token;
    }
    
    public boolean getIsVerified(){
        String sql = String.format("SELECT %s FROM %s WHERE 1", LogonModel.STATUS, LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();
        
        Cursor result = db.rawQuery(sql, null);
        boolean isVerified = false;
        if(result.moveToFirst()){
            int status = result.getInt(0);
            try{
                if(status == 1){
                    isVerified = true;
                }else{
                    isVerified = false;
                }
            }catch (NullPointerException e){
                result.close();
                db.close();
                return false;
            }
        }
        result.close();
        db.close();
        return isVerified;
    }

    public boolean updateToken(String token){
        String query_change_verify_status = String.format("UPDATE %s SET %s = ? WHERE 1;", LogonModel.TABLE_NAME,
                LogonModel.TOKEN);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_verify_status, new String[] {token});
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean updateType(String type){
        String sql = String.format("UPDATE %s SET %s = \"%s\" WHERE 1", LogonModel.TABLE_NAME, LogonModel.TYPE, type);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(sql);
        try{
            db.execSQL(sql);
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean insertLogonResgist(String username, String email){
        String query_user_id = String.format("INSERT INTO %s (%s, %s, %s) values('%s', '%s', '%s')",LogonModel.TABLE_NAME,
                LogonModel.USERNAME, LogonModel.EMAIL, LogonModel.STATUS, username, email, "0");
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean updateVerifyStatus(int status){
        String query_change_verify_status = String.format("UPDATE %s SET %s = %d WHERE 1", LogonModel.TABLE_NAME, LogonModel.STATUS, status);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_verify_status);
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean updateEmail(String email){
        String query_change_email = String.format("UPDATE login SET %s = '%d' WHERE 1", LogonModel.EMAIL, email);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_email);
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean insertLogonLogin(String username, String email, String status, String token, String type){
        String query_user_id = String.format("INSERT INTO %s(%s, %s, %s, %s, %s) values(%s, '%s', '%s', '%s', '%s')", LogonModel.TABLE_NAME,
                LogonModel.STATUS, LogonModel.USERNAME, LogonModel.EMAIL, LogonModel.TOKEN, LogonModel.TYPE, status, username, email, token, type);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public void signOut(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(EmotionModel.SIGN_OUT);
        db.execSQL(EvaluationModel.SIGN_OUT);
        db.execSQL(LogonModel.SIGN_OUT);
        db.execSQL(NoteModel.SIGN_OUT);
        db.execSQL(ProfileGeneralModel.SIGN_OUT);
        db.execSQL(ProfilePatientModel.SIGN_OUT);
        db.execSQL(SleepModel.SIGN_OUT);
        db.close();
    }
    /*************************** LOGON ****************************/


    /************************* PROFILE ****************************/
    public boolean isProfileEmtpy(String type){
        String sql;
        if(type.equals("p")){
            sql = String.format("SELECT * FROM %s WHERE 1", ProfilePatientModel.TABLE_NAME);
        }else if(type.equals("g")){
            sql = String.format("SELECT * FROM %s WHERE 1", ProfileGeneralModel.TABLE_NAME);
        }else{
            return true;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        boolean isEmpty = true;
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            isEmpty = false;
        }
        return isEmpty;
    }

    public Map<String, String> getProfileGeneralDetails(){
        Map<String, String> data = new HashMap<>();
        String sql = String.format("SELECT * FROM %s WHERE 1;", ProfileGeneralModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            String nickname = result.getString(0);
            String emergency = result.getString(1);
            String dob = result.getString(2);
            String isCaffeine = result.getString(3);
            String isDrug = result.getString(4);
            String created = result.getString(5);
            String modified = result.getString(6);
            data.put("nickname", nickname);
            data.put("emergency", emergency);
            data.put("dob", dob);
            data.put("isCaffeine", isCaffeine);
            data.put("isDrug", isDrug);
            data.put("created", created);
            data.put("modified", modified);
            result.close();
            db.close();
            return data;
        }
        result.close();
        System.out.println("Nothing");
        db.close();
        return null;
    }

    public Map<String, String> getProfilePatientDetails(){
        Map<String, String> data = new HashMap<>();
        String sql = String.format("SELECT * FROM %s WHERE 1;", ProfilePatientModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            String nickname = result.getString(0);
            String emergency = result.getString(1);
            String dob = result.getString(2);
            String isCaffeine = result.getString(3);
            String isDrug = result.getString(4);
            String sex = result.getString(5);
            String isPregnant = result.getString(6);
            Float weight = result.getFloat(7);
            Float height = result.getFloat(8);
            Float bmi = result.getFloat(9);
            String d1 = result.getString(10);
            String d2 = result.getString(11);
            String d3 = result.getString(12);
            String d4 = result.getString(13);
            String d5 = result.getString(14);
            String d6 = result.getString(15);
            String created = result.getString(16);
            String modified = result.getString(17);
            System.out.println(String.format("%s %s %s %s %s %s %s %f %f %f %s %s %s %s %s %s %s %s" ,nickname, emergency, dob, isCaffeine, isDrug, sex,  isPregnant,
                    weight, height, bmi, d1, d2, d3, d4, d5, d6, created, modified));
            data.put("nickname", nickname);
            data.put("emergency", emergency);
            data.put("dob", dob);
            data.put("isCaffeine", isCaffeine);
            data.put("isDrug", isDrug);
            data.put("sex", sex);
            data.put("isPregnant", isPregnant);
            data.put("weight", weight + "");
            data.put("height", height + "");
            data.put("bmi", bmi + "");
            data.put("d1", d1);
            data.put("d2", d2);
            data.put("d3", d3);
            data.put("d4", d4);
            data.put("d5", d5);
            data.put("d6", d6);
            data.put("created", created);
            data.put("modified", modified);
        }
        result.close();
        db.close();
        return data;
    }

    public boolean createProfileGeneral(String nickname, String emergencyContact, String dob, int isCaffeine, int isDrug){
        String sql = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %d, %d, date('now'), date('now'));",
                ProfileGeneralModel.TABLE_NAME, ProfileGeneralModel.NICKNAME, ProfileGeneralModel.EMERGENCY_CONTACT, ProfileGeneralModel.DOB, ProfileGeneralModel.IS_CAFFEINE, ProfileGeneralModel.IS_DRUG, ProfileGeneralModel.CREATED_DATE, ProfileGeneralModel.MODIFIED_DATE,
                nickname, emergencyContact, dob, isCaffeine, isDrug);
        System.out.println(sql);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean createProfilePatient(String nickname, String emergencyContact, String dob, int isCaffeine, int isDrug, String sex, int isPregnant, float weight, float height, float bmi, String d1, String d2, String d3, String d4, String d5, String d6){
        String sql = String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %d, %d, '%s', %d, %f, %f, %f, %d, %d, %d, %d, %d, '%s', date('now'), date('now'));",
               ProfilePatientModel.TABLE_NAME, ProfilePatientModel.NICKNAME, ProfilePatientModel.EMERGENCY_CONTACT, ProfilePatientModel.DOB, ProfilePatientModel.IS_CAFFEINE, ProfilePatientModel.IS_DRUG, ProfilePatientModel.SEX,
                ProfilePatientModel.IS_PREGNANT, ProfilePatientModel.WEIGHT, ProfilePatientModel.HEIGHT, ProfilePatientModel.BMI,
                ProfilePatientModel.d1, ProfilePatientModel.d2, ProfilePatientModel.d3, ProfilePatientModel.d4, ProfilePatientModel.d5, ProfilePatientModel.d6,
                ProfilePatientModel.CREATED_DATE, ProfilePatientModel.MODIFIED_DATE,
                nickname, emergencyContact, dob, isCaffeine, isDrug, sex, isPregnant, weight, height, bmi, Integer.parseInt(d1), Integer.parseInt(d2), Integer.parseInt(d3),
                Integer.parseInt(d4), Integer.parseInt(d5), d6);

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public void insertGeneralProfileLogin(Map<String, String> data){
        createProfileGeneral(data.get("nickname"), data.get("emergency_contact"), data.get("dob"), Integer.parseInt(data.get("is_caffeine")), Integer.parseInt(data.get("is_drug_addict")));
    }

    public void insertPatientProfileLogin(Map<String, String> data){
        createProfilePatient(data.get("nickname"), data.get("emergency_contact"), data.get("dob"), Integer.parseInt(data.get("is_caffeine")), Integer.parseInt(data.get("is_drug_addict")),
                data.get("sex"), Integer.parseInt(data.get("is_pregnant")), Float.parseFloat(data.get("weight")), Float.parseFloat(data.get("height")), Float.parseFloat(data.get("bmi")),
                data.get("d1"), data.get("d2"), data.get("d3"), data.get("d4"), data.get("d5"), data.get("d6"));
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
        db.close();
        return obj;
    }

    public ArrayList<RecordObject> getAllRecordObject(int option){
        String sql = "";
        switch (option){
            case 0:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                                "FROM %s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "ORDER BY %s.%s DESC LIMIT 90;",
                        EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                        SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                        ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                        EmotionModel.TABLE_NAME,
                        SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                        ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 1:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                                "FROM %s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "ORDER BY %s.%s DESC LIMIT 180;",
                        EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                        SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                        ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                        EmotionModel.TABLE_NAME,
                        SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                        ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 2:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                                "FROM %s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "ORDER BY %s.%s DESC LIMIT 270;",
                        EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                        SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                        ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                        EmotionModel.TABLE_NAME,
                        SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                        ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 3:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                                "FROM %s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "ORDER BY %s.%s DESC LIMIT 365;",
                        EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                        SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                        ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                        EmotionModel.TABLE_NAME,
                        SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                        ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 4:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
                                "FROM %s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "LEFT JOIN %s ON %s.%s = %s.%s " +
                                "ORDER BY %s.%s DESC LIMIT 730;",
                        EmotionModel.TABLE_NAME, EmotionModel.ID, EmotionModel.TABLE_NAME, EmotionModel.EMOTION, EmotionModel.TABLE_NAME, EmotionModel.LEVEL,
                        SleepModel.TABLE_NAME, SleepModel.ID, SleepModel.TABLE_NAME, SleepModel.TOTAL, SleepModel.TABLE_NAME, SleepModel.START, SleepModel.TABLE_NAME, SleepModel.END,
                        ActivityModel.TABLE_NAME, ActivityModel.ID, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY, EmotionModel.TABLE_NAME, EmotionModel.DATE,
                        EmotionModel.TABLE_NAME,
                        SleepModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, SleepModel.TABLE_NAME, SleepModel.DATE,
                        ActivityModel.TABLE_NAME, EmotionModel.TABLE_NAME, EmotionModel.DATE, ActivityModel.TABLE_NAME, ActivityModel.ACTIVITY,
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 5:
                sql = String.format("SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s " +
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
                break;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(sql, null);
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
            db.close();
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
            db.close();
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
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<MoodObject> getAllMood(){
        String query_get_mood = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC;",
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

    public ArrayList<MoodObject> getFliterMood(int option){
        String sql = "";
        switch (option){
            case 0:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC LIMIT 90;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 1:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC LIMIT 180;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 2:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC LIMIT 270 ;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 3:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC LIMIT 365 ;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 4:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC LIMIT 730 ;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;
            case 5:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s ASC;",
                        EmotionModel.TABLE_NAME, EmotionModel.DATE);
                break;

        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(sql, null);
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

    public ArrayList<MoodObject> getMoodRange(String dStrat, String dEnd){
        String query_get_mood = String.format("SELECT * FROM %s WHERE %s >= '%s' AND %s <= '%s' ORDER BY %s ASC;",
                EmotionModel.TABLE_NAME, EmotionModel.DATE, dStrat, EmotionModel.DATE, dEnd, EmotionModel.DATE);
        System.out.println("DR SQL: " + query_get_mood);
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
        int count = result.getInt(0);
        result.close();
        db.close();
        return count;

    }

    public String getLastDateofMood(){
        String sql = String.format("SELECT %s FROM %s ORDER BY %s DESC LIMIT 1;", EmotionModel.DATE, EmotionModel.TABLE_NAME, EmotionModel.DATE);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        result.close();
        db.close();
        return result.getString(0);
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
            db.close();
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
            db.close();
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
        int count = result.getInt(0);
        result.close();
        db.close();
        return count;
    }
    /*************************** DIARY ****************************/

    /*************************** EVALUATION ****************************/
    public boolean insertEvaluationScore(int score, String type, String date){

        String sql = String.format("UPDATE %s SET %s = %d WHERE %s = '%s';",
                EvaluationModel.TABLE_NAME, type, score, EvaluationModel.date, date);

        if(type.equals(EvaluationModel._2q)){
            sql = String.format("INSERT INTO %s(%s, %s) VALUES(%d, '%s');",
                    EvaluationModel.TABLE_NAME, EvaluationModel._2q, EvaluationModel.date,
                    score, date);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            db.close();
            return false;
        }
        System.out.println(sql);
        db.close();
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

//    public int isEvaluationEmpty(){
//
//    }
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
            db.close();
            return false;
        }
        Log.d("sql", query_insert + " :SUCCESSFULLY" );
        System.out.println(query_insert + " :SUCCESSFULLY");
        return true;
    }

    public boolean updateSleep(int id, float total, String start, String end){

        String sql = String.format("UPDATE %s set %s = %s, %s = '%s', %s = '%s' WHERE %s = %d;",
                SleepModel.TABLE_NAME, SleepModel.TOTAL, total, SleepModel.START, start,
                SleepModel.END, end, SleepModel.ID, id); ;
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(sql);
        }catch (Exception err){
            err.printStackTrace();
            db.close();
            return false;
        }
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

    public ArrayList<SleepObject> getSleep(int option){
        String sql = "";
        switch (option){
            case 0:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC LIMIT 90;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
            case 1:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC LIMIT 180;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
            case 2:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC LIMIT 270;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
            case 3:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC LIMIT 365;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
            case 4:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC LIMIT 730;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
            case 5:
                sql = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC;",
                        SleepModel.TABLE_NAME, SleepModel.DATE);
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(sql, null);
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
        int count = result.getInt(0);
        result.close();
        db.close();
        return count;
    }


    /*************************** SLEEP ****************************/

    public boolean insertSyncMood(String jsonString){
        try{
            JSONObject jsonResponse = new JSONObject(jsonString);
            List<String> allMood = new ArrayList<String>();

            JSONArray moods = jsonResponse.getJSONArray("result");
            for(int i = 0; i < moods.length(); i++){
                JSONObject mood = moods.getJSONObject(i);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try{
                    java.util.Date date = df.parse(mood.getString("date"));
                    insertMood(mood.getInt("mood"), mood.getInt("level"), parseISODateFormatTODateFormar(mood.getString("date")));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }catch (Exception e){
            return false;
        }
        return true;

    }

    public boolean insertSyncSleep(String jsonString){
        try{
            JSONObject jsonResponse = new JSONObject(jsonString);
            List<String> allSleep = new ArrayList<String>();

            JSONArray sleeps = jsonResponse.getJSONArray("result");
            for(int i = 0; i < sleeps.length(); i++){
                JSONObject sleep = sleeps.getJSONObject(i);
                insertSleep((float)sleep.getDouble("total_time"), sleep.getString("start_time"), sleep.getString("end_time"), parseISODateFormatTODateFormar(sleep.getString("date")));
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean insertSyncDiary(String jsonString){
        try{
            JSONObject jsonResponse = new JSONObject(jsonString);
            List<String> allDiary = new ArrayList<String>();

            JSONArray diaries = jsonResponse.getJSONArray("result");
            for(int i = 0; i < diaries.length(); i++){
                JSONObject diary = diaries.getJSONObject(i);
                insertNote(diary.getString("title"), diary.getString("story"), parseISODateFormatTODateFormar(diary.getString("date")));
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean insertSyncEvaluation(String jsonString){
        try{
            JSONObject jsonResponse = new JSONObject(jsonString);
            List<String> allEvaluation = new ArrayList<String>();

            JSONArray evaluations = jsonResponse.getJSONArray("result");
            for(int i = 0; i < evaluations.length(); i++){
                JSONObject evaluation = evaluations.getJSONObject(i);
                insertEvaluationScore(evaluation.getInt("2q"), "q2q", parseISODateFormatTODateFormar(evaluation.getString("date")));
                insertEvaluationScore(evaluation.getInt("9q"), "q9q", parseISODateFormatTODateFormar(evaluation.getString("date")));
                insertEvaluationScore(evaluation.getInt("8q"), "q8q", parseISODateFormatTODateFormar(evaluation.getString("date")));
                insertEvaluationScore(evaluation.getInt("mdq"), "qmdq", parseISODateFormatTODateFormar(evaluation.getString("date")));
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    private String parseISODateFormatTODateFormar(String isoDateFormat){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try{
            java.util.Date date = df.parse(isoDateFormat);
            return date.getYear()+1900+"/"+(date.getMonth()+1)+"/"+date.getDate();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
