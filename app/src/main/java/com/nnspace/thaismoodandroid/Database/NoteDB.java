package com.nnspace.thaismoodandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nnspace.thaismoodandroid.DatabaseModel.NoteModel;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView.DiaryObject;
import com.nnspace.thaismoodandroid.MoodObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NoteDB extends SQLiteOpenHelper {

    public NoteDB(Context context){
        super(context, "ThaisMoodDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = String.format("CREATE TABLE %s (" +
                        "%s int PRIMARY KEY, " +
                        "%s varchar(100) NOT NULL, " +
                        "%s varchar(2000) NOT NULL, " +
                        "%s DATE NOT NULL " +
                        ");", NoteModel.TABLE_NAME,
                NoteModel.column.ID,
                NoteModel.column.TITLE,
                NoteModel.column.NOTE,
                NoteModel.column.DATE
        );
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + NoteModel.TABLE_NAME;
        db.execSQL(DROP_FRIEND_TABLE);
        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);
        onCreate(db);
    }

    public boolean insertNote(String title, String note, String date){

        String query_insert = "INSERT INTO " + NoteModel.TABLE_NAME + " (" + NoteModel.column.NOTE +
                ", " + NoteModel.column.TITLE + ", " + NoteModel.column.DATE + ") " +
                "values('" + title + "', '" + note + "', '" + date + "');";

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(query_insert);
        }catch (Exception err){
            err.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteMood(int id){
        return false;
    }

    public ArrayList<DiaryObject> getAllNote(){
        String query_get_note = String.format("SELECT * FROM %s WHERE 1 ORDER BY %s DESC;",
                NoteModel.TABLE_NAME, NoteModel.column.DATE);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery(query_get_note, null);
        ArrayList<DiaryObject> obj = new ArrayList<>();

        if(result.moveToFirst()){
            do{
                int id = result.getInt(0);
                String title = result.getString(1);
                String note = result.getString(2);
                String date = result.getString(3);
                DiaryObject diaryObject = new DiaryObject(id, title, note, date);
                obj.add(diaryObject);
            }while (result.moveToNext());
        }
        result.close();
        db.close();
        return obj;
    }

    public ArrayList<MoodObject> getMoodWeek(String fromDate, String toDate){
        return null;
    }
}
