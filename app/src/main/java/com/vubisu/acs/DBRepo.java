package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by harryhobbes on 3/14/2016.
 */

public abstract class DBRepo {
    protected DBHelper dbHelper;
    protected String tableName;

    public DBRepo(Context context, String tableName) {
        this.dbHelper = new DBHelper(context);
        this.tableName = tableName;
    }

    protected int insert(ContentValues values) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Inserting Row
        long new_Id = db.insert(tableName, null, values);
        db.close(); // Closing database connection
        return (int) new_Id;
    }

    protected int update(ContentValues values, String columnName, int objectId) {

        if (objectId < 1) {
            return 0;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = columnName + " = ?";
        String[] whereArgs = {String.valueOf(objectId)};

        return db.update(tableName, values, whereClause, whereArgs);
    }

    protected Cursor getList(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                tableName,                            // The table to query
                columns,                                  // The columns to return
                selection,                                     // The columns for the WHERE clause
                selectionArgs,                                     // The values for the WHERE clause
                groupBy,                                     // don't group the rows
                having,                                     // don't filter by row groups
                orderBy                                      // don't sort
        );
        // looping through all rows and adding to list
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }
}