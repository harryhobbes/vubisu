package com.vubisu.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by harryhobbes on 3/14/2016.
 */


public class ServiceRepo extends DBRepo {

    public ServiceRepo(Context context) {
        super(context, Service.TABLE);
    }

    private ContentValues getContentValues(String context, Service service) {
        ContentValues values = new ContentValues();

        values.put(Service.KEY_GROUP,   service.group);
        values.put(Service.KEY_NAME,    service.name);
        values.put(Service.KEY_PRICE,   service.price);

        return values;
    }

    private String[] getStringArray(String context) {

        String[] columns;

        switch (context) {
            case "view":
            case "list":
            default:
                columns = new String[]{
                        "rowid as " + Service._ID,
                        Service.KEY_ID,
                        Service.KEY_GROUP,
                        Service.KEY_NAME,
                        Service.KEY_PRICE
                };
                break;
        }

        return columns;
    }

    public int insert(Service service) {
        return super.insert(getContentValues("insert", service));
    }

    public int update(Service service) {

        if (service.service_ID < 1) {
            return 0;
        }

        return super.update(getContentValues("update", service), Service.KEY_ID, service.service_ID);
    }

    public Cursor getServiceList() {
        return super.getList(
                getStringArray("list"),
                null,
                null,
                null,
                null,
                null
        );


    }

    public Cursor getServiceListByKeyword(String search) {
        String selection = Service.KEY_GROUP + " LIKE ? or " +
                Service.KEY_NAME + " LIKE ?";

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

    public Service getServiceById(int id){
        String selection = Service.KEY_ID + " = ?";

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
        Service service = new Service();

        if (cursor.moveToFirst()) {
            do {
                service.service_ID =cursor.getInt(cursor.getColumnIndex(Service.KEY_ID));
                service.group =cursor.getString(cursor.getColumnIndex(Service.KEY_GROUP));
                service.name =cursor.getString(cursor.getColumnIndex(Service.KEY_NAME));
                service.price =cursor.getInt(cursor.getColumnIndex(Service.KEY_PRICE));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return service;
    }
}