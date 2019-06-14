package com.nnspace.thaismoodandroid.DatabaseModel;

public class EvaluationModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "evaluation";
    public static final String _2q = "q2q";
    public static final String _9q = "q9q";
    public static final String _8q = "q8q";
    public static final String mdq = "qmdq";
    public static final String date = "date";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
            "%s int DEFAULT -1, " +
            "%s int DEFAULT -1, " +
            "%s int DEFAULT -1, " +
            "%s int DEFAULT -1, " +
            "%s date NOT NULL" +
            ");", TABLE_NAME, _2q, _9q, _8q, mdq, date);
}
