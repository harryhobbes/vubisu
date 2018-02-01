package com.vubisu.acs;

/**
 * Created by harryhobbes on 15/01/2018.
 */
public class AppointmentService {
    public static final String TABLE = "AppointmentService";

    public static final String KEY_APPOINTMENT_ID = "appointment_id";
    public static final String KEY_SERVICE_ID = "service_id";
    public static final String KEY_ACTUAL_PRICE = "actual_price";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE + " (" +
            KEY_APPOINTMENT_ID + " INTEGER, " +
            KEY_SERVICE_ID + " INTEGER, " +
            KEY_ACTUAL_PRICE + " INTEGER, " +
            "FOREIGN KEY(" + KEY_APPOINTMENT_ID + ") REFERENCES " +
            Appointment.TABLE + "(" + Appointment.KEY_ID + "), " +
            "FOREIGN KEY(" + KEY_SERVICE_ID + ") REFERENCES " +
            Service.TABLE + "(" + Service.KEY_ID + ") " +
            ");" +
            "CREATE UNIQUE INDEX index_appointment_id_and_service_id ON " +
            TABLE + "(" + KEY_APPOINTMENT_ID + ", " + KEY_SERVICE_ID + ");";

    // property help us to keep data
    public int appointment_ID;
    public int service_ID;
    public int actual_price;
}
