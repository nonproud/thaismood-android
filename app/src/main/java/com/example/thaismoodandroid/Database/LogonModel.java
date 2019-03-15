package com.example.thaismoodandroid.Database;

class LogonModel {
    public static final String DATABASE_NAME = "LogonInfo";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "isLogin";

    public class column{
        public static final String ID = "token";
        public static final String STATUS = "status";
        public static final String EMAIL = "email";
        public static final String TOKEN = "token";
    }

}
