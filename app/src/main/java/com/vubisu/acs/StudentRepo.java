package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tan on 3/14/2016.
 */


public class StudentRepo {
    private DBHelper dbHelper;

    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_notes, student.notes);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_phone, student.phone);
        values.put(Student.KEY_name, student.name);

        // Inserting Row
        long student_Id = db.insert(Student.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public int update(Student student) {

        if (student.student_ID < 1) {
            return 0;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_notes, student.notes);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_phone, student.phone);
        values.put(Student.KEY_name, student.name);

        String whereClause = student.KEY_ID + " = ?";
        String[] whereArgs = {String.valueOf(student.student_ID)};

        return db.update(student.TABLE, values, whereClause, whereArgs);
    }

    public Cursor getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "rowid as " + Student.KEY_ROWID,
                Student.KEY_ID,
                Student.KEY_name,
                Student.KEY_phone,
                Student.KEY_email
        };

        Cursor cursor = db.query(
                Student.TABLE,                            // The table to query
                columns,                                  // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        // looping through all rows and adding to list
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    public Cursor getStudentListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "rowid as " + Student.KEY_ROWID,
                Student.KEY_ID,
                Student.KEY_name,
                Student.KEY_phone,
                Student.KEY_email
        };

        String selection = Student.KEY_name + " LIKE ? or " +
                Student.KEY_phone + " LIKE ?";

        String[] selectionArgs = {
                "%" + search + "%",
                "%" + search + "%"
        };

        Cursor cursor = db.query(
                Student.TABLE,                            // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        // looping through all rows and adding to list
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Student getStudentById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "rowid as " + Student.KEY_ROWID,
                Student.KEY_ID,
                Student.KEY_name,
                Student.KEY_phone,
                Student.KEY_email,
                Student.KEY_notes,
                Student.KEY_age
        };

        String selection = Student.KEY_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                Student.TABLE,                            // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        int iCount =0;
        Student student = new Student();

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.phone =cursor.getString(cursor.getColumnIndex(Student.KEY_phone));
                student.email  =cursor.getString(cursor.getColumnIndex(Student.KEY_email));
                student.notes = cursor.getString(cursor.getColumnIndex(Student.KEY_notes));
                student.age =cursor.getInt(cursor.getColumnIndex(Student.KEY_age));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }
}