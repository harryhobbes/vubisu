package com.instinctcoder.searchwidget;

/**
 * Created by Tan on 3/14/2016.
 */
public class Student {
    // Labels table name
    public static final String TABLE = "Student";

    // Labels Table Columns names
    public static final String KEY_ROWID = "_id";
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_phone = "phone";
    public static final String KEY_email = "email";
    public static final String KEY_notes = "notes";
    public static final String KEY_age = "age";

    // property help us to keep data
    public int student_ID;
    public String name;
    public String phone;
    public String email;
    public String notes;
    public int age;
}