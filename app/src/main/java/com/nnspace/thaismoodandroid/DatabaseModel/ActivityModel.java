package com.nnspace.thaismoodandroid.DatabaseModel;

public class ActivityModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "activity";
    public static final String ID = "id";
    public static final String ACTIVITY = "activity";
    public static final String LEVEL = "level";
    public static final String DATE = "date";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s varchar(200) NOT NULL, " +
                    "%s DATE NOT NULL " +
                    ");", TABLE_NAME, ID, ACTIVITY, DATE);

    public static final String SIGN_OUT = String.format("DELETE FROM %s WHERE 1", TABLE_NAME);

}