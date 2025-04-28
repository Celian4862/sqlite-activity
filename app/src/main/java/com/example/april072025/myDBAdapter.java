package com.example.april072025;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBAdapter {
    myDBHelper dbHelper;

    public myDBAdapter(Context context) {
        dbHelper = new myDBHelper(context);
    }

    public String getData() {
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
        StringBuilder stringBuffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int temp = cursor.getColumnIndex(myDBHelper.UID);
            int cid = cursor.getInt(Math.max(temp, 1)); // cid means customer ID

            temp = cursor.getColumnIndex(myDBHelper.NAME);
            String name = cursor.getString(Math.max(temp, 1));

            temp = cursor.getColumnIndex(myDBHelper.MyPASSWORD);
            String pass = cursor.getString(Math.max(temp, 1));
            stringBuffer.append(cid)
                    .append("  ")
                    .append(name)
                    .append("  ")
                    .append(pass);
        }
        return stringBuffer.toString();
    }

    public long insertData (String name, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NAME, name);
        contentValues.put(dbHelper.MyPASSWORD, pass);
        long id = sqLiteDatabase.insert(dbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public int deleteData(String uname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] whereArgs = {uname};
        int count = db.delete(
                myDBHelper.TABLE_NAME,
                myDBHelper.NAME + " =?", whereArgs);
        return count;
    }

    static class myDBHelper extends SQLiteOpenHelper {
        private Context context;

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
            this.context = context;
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
