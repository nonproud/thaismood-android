package com.nnspace.thaismoodandroid.DatabaseModel;

public class ProfileGeneralModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "profile";

    public static final String NICKNAME = "nickname";
    public static final String EMERGENCY_CONTACT = "emergency_contact";
    public static final String DOB = "dob";
    public static final String IS_CAFFEINE = "is_caffeine_addict";
    public static final String IS_DRUG = "is_drug_addict";
    public static final String CREATED_DATE = "created";
    public static final String MODIFIED_DATE = "last_modified";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s varchar(50) NOT NULL," +
                    "%s varchar(10) NOT NULL," +
                    "%s date NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s date," +
                    "%s date);", TABLE_NAME, NICKNAME, EMERGENCY_CONTACT, DOB, IS_CAFFEINE, IS_DRUG, CREATED_DATE, MODIFIED_DATE);
}