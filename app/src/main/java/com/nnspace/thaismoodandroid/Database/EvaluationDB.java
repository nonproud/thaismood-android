package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.DatabaseModel.LogonModel;

public class EvaluationDB  extends SQLiteOpenHelper {

    public EvaluationDB(Context context) {
        super(context, EvaluationModel.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVALUATION_CHECK_TABLE = String.format("CREATE TABLE %s (" +
                        "%s int NOT NULL DEFAULT 0," +
                        "%s varchar(20) NOT NULL DEFAULT ''," +
                        "%s varchar(50) NOT NULL DEFAULT ''," +
                        "%s varchar(300) NOT NULL DEFAULT ''" +
                        ");", EvaluationModel.TABLE_NAME,
                LogonModel.column.STATUS,
                LogonModel.column.ID,
                LogonModel.column.EMAIL,
                LogonModel.column.TOKEN
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
