package com.nnspace.thaismoodandroid.DatabaseModel;

public class LogonModel {
    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "isLogin";

    public class column{
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String EMAIL = "email";
        public static final String TOKEN = "token";
    }

}
