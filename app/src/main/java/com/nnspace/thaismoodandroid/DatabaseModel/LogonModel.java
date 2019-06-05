package com.nnspace.thaismoodandroid.DatabaseModel;

public class LogonModel {
    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "isLogin";

    public static final String USERNAME = "username";
    public static final String STATUS = "status";
    public static final String EMAIL = "email";
    public static final String TOKEN = "token";
    public static final String TYPE = "type";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s int NOT NULL DEFAULT 0, " +
                    "%s varchar(100) DEFAULT '', " +
                    "%s varchar(50) DEFAULT '', " +
                    "%s varchar(1000) DEFAULT ''," +
                    "%s char(1)" +
                    ");", TABLE_NAME, STATUS, USERNAME, EMAIL, TOKEN, TYPE);

}
