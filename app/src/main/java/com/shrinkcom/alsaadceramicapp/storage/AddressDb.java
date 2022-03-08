/*
package com.shrinkcom.alsaadhomeapp.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AddressDb {

    public final static String id_ = "id";
    public final static String user_id = "user_id";



    final static String ID_Table = "ID_TABLE";
    final static String DATABASE_NAME = "AlSaadHomeAPP";



    private static final String TAG = "AddressDB";
    final DatabaseHelper dbHelper;
    final Context context;
    SQLiteDatabase db;
    final static int DATABASE_VERSION = 1;
    final static String DATABASE_CREATE = "create table " + ID_Table + "(" + id_ + " integer primary key autoincrement,"
            + user_id + " integer);";



    public AddressDb(Context con) {
        this.context = con;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    // TODO: 3/24/2020 inserdata

    public long insertData( String user_id) {
        ContentValues values = new ContentValues();
        values.put(AddressDb.user_id, user_id);

        return db.insert(ID_Table, null, values);

    }

    public Cursor getAllData() {

        Cursor c =  db.rawQuery("SELECT * FROM " + ID_Table ,null);
        return c;

    }





    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            try {
                database.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

            onCreate(db);
        }
    }
}
*/
