package com.example.april072025;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

public class myDBAdapter {
    myDBHelper dbHelper;

    public myDBAdapter(Context context) {
        dbHelper = new myDBHelper(context);
    }

    public String[] getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {
                myDBHelper.UID,
                myDBHelper.NAME,
                myDBHelper.MyPASSWORD
        };
        Cursor cursor = db.query(myDBHelper.TABLE_NAME, columns,
                null,
                null,
                null,
                null,
                null);
        ArrayList<String> stringBuffer = new ArrayList<>();
        while (cursor.moveToNext()) {
            int temp = cursor.getColumnIndex(myDBHelper.UID);
            int cid = cursor.getInt(temp); // cid means customer ID

            temp = cursor.getColumnIndex(myDBHelper.NAME);
            String name = cursor.getString(temp);

            temp = cursor.getColumnIndex(myDBHelper.MyPASSWORD);
            String pass = cursor.getString(temp);

            stringBuffer.add(String.format(new Locale("en"), "%d %s %s", cid, name, pass));
        }
        cursor.close();
        String[] rtn_string = new String[stringBuffer.size()];
        int i = 0;
        for (String item : stringBuffer) {
            rtn_string[i++] = item;
        }
        return rtn_string;
    }

    public long insertData (String name, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBHelper.NAME, name);
        contentValues.put(myDBHelper.MyPASSWORD, pass);
        return sqLiteDatabase.insert(myDBHelper.TABLE_NAME, null, contentValues);
    }

    public int deleteData(String uname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {uname};
        return db.delete(
                myDBHelper.TABLE_NAME,
                myDBHelper.NAME + " =?", whereArgs);
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBHelper.NAME, newName);
        String[] whereArgs = {oldName};
        return db.update(myDBHelper.TABLE_NAME,
                contentValues,
                myDBHelper.NAME + " =?", whereArgs);
    }

    static class myDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDB";
        private static final String TABLE_NAME = "myTable";
        private static final int DATABASE_Version = 1;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String MyPASSWORD = "Password";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " VARCHAR(255),"
                + MyPASSWORD + " VARCHAR(255));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME;

        public myDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
