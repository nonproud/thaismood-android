package com.nnspace.thaismoodandroid.DatabaseModel;

public class SleepModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "sleep";

    public class column{
        public static final String ID = "id";
        public static final String TOTAL = "total_time";
        public static final String START = "start_time";
        public static final String END = "end_time";
        public static final String DATE = "date";
    }

}