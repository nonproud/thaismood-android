package com.nnspace.thaismoodandroid.DatabaseModel;

public class NoteModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "diary";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String NOTE = "diary";
    public static final String DATE = "date";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s varchar(100) NOT NULL, " +
                    "%s varchar(2000) NOT NULL, " +
                    "%s DATE NOT NULL);", TABLE_NAME, ID, TITLE, NOTE, DATE);
}