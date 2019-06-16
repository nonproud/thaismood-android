package com.nnspace.thaismoodandroid.DatabaseModel;

public class ExerciseModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "exercise";

    public static final String ID = "id";
    public static final String STEP = "step_count";
    public static final String DATE = "date";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY," +
            "%s int NOT NULL," +
            "%s date NOT NULL" +
            ");", TABLE_NAME, ID, STEP, DATE);

    public static final String SIGN_OUT = String.format("DELETE FROM %s WHERE 1", TABLE_NAME);

}