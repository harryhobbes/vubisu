package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

/**
 * Created by harryhobbes on 3/14/2016.
 */
public class AppointmentRepo extends DBRepo {

    final String APP_TABLE = "appointment";
    final String SER_TABLE = "service";
    final String APPSER_TABLE = "app_ser";

    public AppointmentRepo(Context context) {
        super(context, Appointment.TABLE);
    }

    private ContentValues getContentValues(String context, Appointment appointment) {
        ContentValues values = new ContentValues();

        values.put(Appointment.KEY_start, appointment.start);
        values.put(Appointment.KEY_notes, appointment.notes);
        values.put(Appointment.KEY_customer_id, appointment.customer_ID);

        return values;
    }

    private String[] getStringArray(String context) {

        String[] columns;

        switch (context) {
            case "view":
                columns = new String[]{
                        "rowid as " + Appointment._ID,
                        Appointment.KEY_ID,
                        Appointment.KEY_start,
                        Appointment.KEY_notes
                };
                break;
            case "list":
            default:
                columns = new String[]{
                        "rowid as " + Appointment._ID,
                        Appointment.KEY_ID,
                        Appointment.KEY_start,
                        Appointment.KEY_notes
                };
                break;
        }

        return columns;
    }

    public int insert(Appointment appointment) {
        return super.insert(getContentValues("insert", appointment));
    }

    public int update(Appointment appointment) {

        if (appointment.appointment_ID < 1) {
            return 0;
        }

        return super.update(getContentValues("update", appointment), Appointment.KEY_ID, appointment.appointment_ID);
    }

    public Cursor getAppointmentList() {
        return super.getList(
                getStringArray("list"),
                null,
                null,
                null,
                null,
                null
        );
    }

    protected String getRawAppointmentWithServicesQueryString() {
        return "SELECT * FROM " + Appointment.TABLE + " " + APP_TABLE +
                " INNER JOIN " + AppointmentService.TABLE + " " + APPSER_TABLE + " ON " + APP_TABLE + "." + Appointment.KEY_ID + " = " + APPSER_TABLE + "." +
                AppointmentService.KEY_APPOINTMENT_ID +
                " INNER JOIN " + Service.TABLE + " " + SER_TABLE + " ON " + APPSER_TABLE + "." + AppointmentService.KEY_SERVICE_ID + " = " + SER_TABLE + "." +
                Service.KEY_ID;
    }

    public Cursor getAppointmentListWithServicesByCustomerId(int customerId) {

        String query = getRawAppointmentWithServicesQueryString() +
                " WHERE " + APP_TABLE + "." + Appointment.KEY_customer_id + " = ?";

        String[] selectionArgs = {Integer.toString(customerId)};

        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                query,
                selectionArgs
        );

        // looping through all rows and adding to list
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }

    public Cursor getAppointmentWithServicesById(int id){
        String query = getRawAppointmentWithServicesQueryString() +
                " WHERE " + APP_TABLE + "." + Appointment.KEY_ID + " = ?";

        String[] selectionArgs = {Integer.toString(id)};

        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                query,
                selectionArgs
        );

        // looping through all rows and adding to list
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }
}