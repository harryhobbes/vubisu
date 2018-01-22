package com.vubisu.acs;

import android.provider.BaseColumns;

import java.text.SimpleDateFormat;

/**
 * Created by harryhobbes on 15/01/2018.
 */
public class Appointment implements BaseColumns {
    public static final String TABLE = "Appointment";

    public static final String KEY_ID = "id";
    public static final String KEY_start = "start";
    public static final String KEY_customer_id = "customer_id";
    public static final String KEY_notes = "notes";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_start + " INTEGER, " +
            KEY_notes + " TEXT, " +
            KEY_customer_id + " INTEGER, " +
            "FOREIGN KEY(" + KEY_customer_id + ") REFERENCES " +
            Student.TABLE + "(" + Student.KEY_ID + ") " + ")";

    // property help us to keep data
    public int appointment_ID;
    public int start;
    public int customer_ID;
    public String notes;
}
