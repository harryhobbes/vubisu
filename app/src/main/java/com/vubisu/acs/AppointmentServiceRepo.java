package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by harryhobbes on 3/14/2016.
 */
public class AppointmentServiceRepo extends DBRepo {

    public AppointmentServiceRepo(Context context) {
        super(context, AppointmentService.TABLE);
    }

    private ContentValues getContentValues(String context, AppointmentService appointmentService) {
        ContentValues values = new ContentValues();

        values.put(AppointmentService.KEY_APPOINTMENT_ID,   appointmentService.appointment_ID);
        values.put(AppointmentService.KEY_SERVICE_ID,       appointmentService.service_ID);
        values.put(AppointmentService.KEY_ACTUAL_PRICE,    appointmentService.actual_price);

        return values;
    }

    private String[] getStringArray(String context) {

        String[] columns;

        switch (context) {
            case "view":
            case "list":
            default:
                columns = new String[]{
                        AppointmentService.KEY_APPOINTMENT_ID,
                        AppointmentService.KEY_SERVICE_ID,
                        AppointmentService.KEY_ACTUAL_PRICE
                };
                break;
        }

        return columns;
    }

    public int insert(AppointmentService appointmentService) {
        return update(appointmentService);
    }

    public int update(AppointmentService appointmentService) {

        if (appointmentService.appointment_ID < 1 || appointmentService.service_ID < 1) {
            return 0;
        }

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return (int) db.replaceOrThrow(
                tableName,
                null,
                getContentValues("update", appointmentService)
        );
    }
}