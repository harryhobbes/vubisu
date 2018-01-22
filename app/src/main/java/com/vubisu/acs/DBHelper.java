package com.vubisu.acs;

/**
 * Created by harryhobbes on 3/14/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "searchwidget.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(Student.CREATE_TABLE);
        db.execSQL(Appointment.CREATE_TABLE);
        db.execSQL(Service.CREATE_TABLE);
        db.execSQL(AppointmentService.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Appointment.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Service.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AppointmentService.TABLE);

        // Create tables again
        onCreate(db);

    }

}