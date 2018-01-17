package com.vubisu.acs;

import android.provider.BaseColumns;

/**
 * Created by harryhobbes on 15/01/2018.
 */
public class Service implements BaseColumns {
    public static final String TABLE_NAME = "service";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CREATED_AT = "created_at";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PRICE + " INTEGER, " +
            COLUMN_CREATED_AT + " INTEGER" + ")";
}
