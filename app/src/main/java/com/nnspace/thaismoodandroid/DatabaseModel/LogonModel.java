package com.nnspace.thaismoodandroid.DatabaseModel;

public class LogonModel {
    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "isLogin";

    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String EMAIL = "email";
    public static final String TOKEN = "token";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s int NOT NULL DEFAULT 0," +
                    "%s varchar(20) NOT NULL DEFAULT ''," +
                    "%s varchar(50) NOT NULL DEFAULT ''," +
                    "%s varchar(300) NOT NULL DEFAULT ''" +
                    ");", TABLE_NAME, STATUS, ID, EMAIL, TOKEN);

}
