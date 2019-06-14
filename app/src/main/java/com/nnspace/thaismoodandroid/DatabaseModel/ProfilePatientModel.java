package com.nnspace.thaismoodandroid.DatabaseModel;

public class ProfilePatientModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "profile_patient";

    public static final String NICKNAME = "nickname";
    public static final String EMERGENCY_CONTACT = "emergency_contact";
    public static final String DOB = "dob";
    public static final String IS_CAFFEINE = "is_caffeine_addict";
    public static final String IS_DRUG = "is_drug_addict";
    public static final String CREATED_DATE = "created";
    public static final String MODIFIED_DATE = "last_modified";
    public static final String SEX = "sex";
    public static final String IS_PREGNANT = "is_pregnant";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String BMI = "bmi";
    public static final String d1 = "d1";
    public static final String d2 = "d2";
    public static final String d3 = "d3";
    public static final String d4 = "d4";
    public static final String d5 = "d5";
    public static final String d6 = "d6";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s varchar(50) NOT NULL," +
                    "%s varchar(10) NOT NULL," +
                    "%s date NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s varchar(6)," +
                    "%s bit," +
                    "%s float," +
                    "%s float," +
                    "%s float," +
                    "%s bit," +
                    "%s bit," +
                    "%s bit," +
                    "%s bit," +
                    "%s bit," +
                    "%s varchar(50)," +
                    "%s date," +
                    "%s date);", TABLE_NAME, NICKNAME, EMERGENCY_CONTACT, DOB, IS_CAFFEINE, IS_DRUG, SEX, IS_PREGNANT,
            WEIGHT, HEIGHT, BMI, d1, d2, d3, d4, d5, d6, CREATED_DATE, MODIFIED_DATE);

}