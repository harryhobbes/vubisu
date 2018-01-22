package com.vubisu.acs;

import android.provider.BaseColumns;

import java.text.SimpleDateFormat;

/**
 * Created by harryhobbes on 15/01/2018.
 */
public class AppointmentService {
    public static final String TABLE = "AppointmentService";

    public static final String KEY_APPOINTMENT_ID = "appointment_id";
    public static final String KEY_SERVICE_ID = "service_id";
    public static final String KEY_SERVICE_PRICE = "price";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE + " (" +
            KEY_APPOINTMENT_ID + " INTEGER, " +
            KEY_SERVICE_ID + " INTEGER, " +
            KEY_SERVICE_PRICE + " INTEGER, " +
            "FOREIGN KEY(" + KEY_APPOINTMENT_ID + ") REFERENCES " +
            Appointment.TABLE + "(" + Appointment.KEY_ID + "), " +
            "FOREIGN KEY(" + KEY_SERVICE_ID + ") REFERENCES " +
            Service.TABLE + "(" + Service.KEY_ID + ") " +
            ")";

    // property help us to keep data
    public int appointment_ID;
    public int service_ID;
    public int price;
}
