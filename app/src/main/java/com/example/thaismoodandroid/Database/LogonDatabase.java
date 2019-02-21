package com.example.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;

import static android.content.ContentValues.TAG;

public class LogonDatabase extends SQLiteOpenHelper {

    public LogonDatabase(Context context) {
        super(context, "LoginCheckDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_CHECK_TABLE = String.format("CREATE TABLE %s (" +
                "%s bit NOT NULL DEFAULT 0," +
                "%s varchar(20) NOT NULL DEFAULT ''," +
                "%s varchar(30) NOT NULL DEFAULT ''," +
                "%s varchar(50) NOT NULL DEFAULT ''," +
                "%s varchar(300) NOT NULL DEFAULT ''" +
                ");",LogonModel.TABLE_NAME,
                LogonModel.column.STATUS,
                LogonModel.column.FNAME,
                LogonModel.column.LNAME,
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
}
