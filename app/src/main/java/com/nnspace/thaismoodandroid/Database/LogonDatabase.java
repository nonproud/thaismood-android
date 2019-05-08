package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.LogonModel;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LogonDatabase extends SQLiteOpenHelper {

    public LogonDatabase(Context context) {
        super(context, LogonModel.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_CHECK_TABLE = String.format("CREATE TABLE %s (" +
                "%s int NOT NULL DEFAULT 0," +
                "%s varchar(20) NOT NULL DEFAULT ''," +
                "%s varchar(50) NOT NULL DEFAULT ''," +
                "%s varchar(300) NOT NULL DEFAULT ''" +
                ");", LogonModel.TABLE_NAME,
                LogonModel.column.STATUS,
                LogonModel.column.ID,
                LogonModel.column.EMAIL,
                LogonModel.column.TOKEN
                );
//        System.out.println(CREATE_LOGIN_CHECK_TABLE);
        db.execSQL(CREATE_LOGIN_CHECK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + LogonModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
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
        String query_change_verify_status = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.column.TOKEN, token);
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
                LogonModel.column.ID, LogonModel.column.EMAIL, LogonModel.column.STATUS, id, email, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean updateVerifyStatus(int status){
        String query_change_verify_status = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.column.STATUS, status);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_change_verify_status);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean updateEmail(String email){
        String query_change_email = String.format("UPDATE login SET %s = '%d' WHERE 1'", LogonModel.column.EMAIL, email);
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
                LogonModel.column.STATUS, LogonModel.column.ID, LogonModel.column.EMAIL, LogonModel.column.TOKEN, id, email, status, token);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }

}
