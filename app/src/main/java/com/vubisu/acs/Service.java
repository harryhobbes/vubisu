package com.vubisu.acs;

import android.provider.BaseColumns;

/**
 * Created by harryhobbes on 15/01/2018.
 */
public class Service implements BaseColumns {
    public static final String TABLE = "Service";

    public static final String KEY_ID = "id";
    public static final String KEY_GROUP = "group_name";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_GROUP + " TEXT, " +
            KEY_NAME + " TEXT, " +
            KEY_PRICE + " INTEGER" + ")";

    public int service_ID;
    public String group;
    public String name;
    public int price;
}
