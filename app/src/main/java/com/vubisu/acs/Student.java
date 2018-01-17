package com.vubisu.acs;

import android.provider.BaseColumns;

/**
 * Created by harryhobbes on 3/14/2016.
 */
public class Student implements BaseColumns{
    // Labels table name
    public static final String TABLE = "Student";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_phone = "phone";
    public static final String KEY_email = "email";
    public static final String KEY_notes = "notes";
    public static final String KEY_age = "age";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + Student.TABLE  + "("
            + Student.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + Student.KEY_name + " TEXT, "
            + Student.KEY_phone + " TEXT, "
            + Student.KEY_notes + " TEXT, "
            + Student.KEY_age + " INTEGER, "
            + Student.KEY_email + " TEXT )";

    // property help us to keep data
    public int student_ID;
    public String name;
    public String phone;
    public String email;
    public String notes;
    public int age;
}