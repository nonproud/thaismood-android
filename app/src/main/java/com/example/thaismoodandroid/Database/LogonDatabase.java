package com.example.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class LogonDatabase extends SQLiteOpenHelper {

    public LogonDatabase(Context context) {
        super(context, "LoginCheckDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_CHECK_TABLE = String.format("CREATE TABLE %s (" +
                "%s int NOT NULL DEFAULT 0," +
                "%s varchar(20) NOT NULL DEFAULT ''," +
                "%s varchar(50) NOT NULL DEFAULT ''," +
                "%s varchar(300) NOT NULL DEFAULT ''" +
                ");",LogonModel.TABLE_NAME,
                LogonModel.column.STATUS,
                LogonModel.column.ID,
                LogonModel.column.EMAIL,
                LogonModel.column.TOKEN
                );
        System.out.println(CREATE_LOGIN_CHECK_TABLE);
        db.execSQL(CREATE_LOGIN_CHECK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + LogonModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }

    public boolean isLogon(){
        String query_logon_tabel = String.format("SELECT * FROM %s", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.getCount() > 0){
            return true;
        }

        return false;
    }

    public String getUserID(){
        String query_logon_tabel = String.format("SELECT * FROM %s WHERE 1", LogonModel.TABLE_NAME);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_logon_tabel, null);
        if(result.getCount() > 0){
            return result.getString(1);
        }

        return null;
    }

    public boolean insertLogonResgist(String id, String email){
        String query_user_id = String.format("INSERT INTO %s (%s, %s) values('%s', '%s')",LogonModel.TABLE_NAME,
                LogonModel.column.ID, LogonModel.column.EMAIL, id, email);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }

    public boolean insertLogonLogin(String id, String email, String status){
        String query_user_id = String.format("INSERT INTO %s (%s, %s, %s) values(%s, '%s', '%s')", LogonModel.TABLE_NAME,
                LogonModel.column.STATUS, LogonModel.column.ID, LogonModel.column.EMAIL, id, email, status);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_user_id);
        }catch (Exception err){
            return false;
        }
        return true;
    }
}
