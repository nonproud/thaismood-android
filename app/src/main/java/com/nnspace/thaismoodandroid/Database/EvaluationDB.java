package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;

import static android.content.ContentValues.TAG;

public class EvaluationDB  extends SQLiteOpenHelper {

    public EvaluationDB(Context context) {
        super(context, EvaluationModel.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s int NOT NULL DEFAULT ''," +
                        "%s int NOT NULL DEFAULT ''," +
                        "%s int NOT NULL DEFAULT ''" +
                        "%s int NOT NULL DEFAULT ''" +
                        "%s date NOT NULL" +
                        ");", EvaluationModel.TABLE_NAME,
                EvaluationModel.column._2q,
                EvaluationModel.column._9q,
                EvaluationModel.column._8q,
                EvaluationModel.column.mdq,
                EvaluationModel.column.date
        );

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + EvaluationModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }
}
