package com.nnspace.thaismoodandroid.DatabaseModel;

public class ProfilePatientModel {

    public static final String DATABASE_NAME = "ThaisMoodDB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "profile_patient";


    public static final String ID = "id";
    public static final String USERNAME = "username";
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
    public static final String disease = "disease";

    public static final String CREATE_TABLE_STRING = String.format("CREATE TABLE %s (" +
                    "%s varchar(100) NOT NULL," +
                    "%s date NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s bit NOT NULL," +
                    "%s varchar(6)," +
                    "%s bit," +
                    "%s float," +
                    "%s float," +
                    "%s float," +
                    "%s varchar(500)," +
                    "%s date," +
                    "%s date);", TABLE_NAME, USERNAME, DOB, IS_CAFFEINE, IS_DRUG, SEX, IS_PREGNANT,
            WEIGHT, HEIGHT, BMI, disease, CREATED_DATE, MODIFIED_DATE);

}