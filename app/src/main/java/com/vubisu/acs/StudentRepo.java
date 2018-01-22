package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by harryhobbes on 3/14/2016.
 */
public class StudentRepo extends DBRepo {

    public StudentRepo(Context context) {
        super(context, Student.TABLE);
    }

    private ContentValues getContentValues(String context, Student student) {
        ContentValues values = new ContentValues();

        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_notes, student.notes);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_phone, student.phone);
        values.put(Student.KEY_name, student.name);

        return values;
    }

    private String[] getStringArray(String context) {

        String[] columns;

        switch (context) {
            case "view":
                columns = new String[]{
                        "rowid as " + Student._ID,
                        Student.KEY_ID,
                        Student.KEY_name,
                        Student.KEY_phone,
                        Student.KEY_email,
                        Student.KEY_notes,
                        Student.KEY_age
                };
                break;
            case "list":
            default:
                columns = new String[]{
                        "rowid as " + Student._ID,
                        Student.KEY_ID,
                        Student.KEY_name,
                        Student.KEY_phone,
                        Student.KEY_email
                };
                break;
        }

        return columns;
    }

    public int insert(Student student) {
        return super.insert(getContentValues("insert", student));
    }

    public int update(Student student) {

        if (student.student_ID < 1) {
            return 0;
        }

        return super.update(getContentValues("update", student), Student.KEY_ID, student.student_ID);
    }

    public Cursor getStudentList() {
        return super.getList(
                getStringArray("list"),
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getStudentListByKeyword(String search) {
        String selection = Student.KEY_name + " LIKE ? or " +
                Student.KEY_phone + " LIKE ?";

        String[] selectionArgs = {
                "%" + search + "%",
                "%" + search + "%"
        };

        return super.getList(
                getStringArray("list"),
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public Student getStudentById(int id){
        String selection = Student.KEY_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = super.getList(
                getStringArray("view"),
                selection,
                selectionArgs,
                null,
                null,
                null
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
        return student;
    }
}