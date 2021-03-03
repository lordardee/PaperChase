package com.example.paperchase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 7;

    private static final String TABLE_NAME = "course_table";
    private static final String COURSE_ID = "ID";
    private static final String COURSE_NAME = "course_name";
    private static final String QR_VALUES = "qr_values";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COURSE_NAME + " TEXT," + QR_VALUES + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addQrData(String qrData, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + QR_VALUES + "= '" + qrData + "' WHERE " + COURSE_NAME + " = '" + name + "'";
        db.execSQL(query);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_NAME, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COURSE_NAME + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getQrValues(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + QR_VALUES + " FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String name){ //Behöver nog inte denna. kanske till delete.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COURSE_ID + " FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteData(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //id = 4; //Debug funkar inte att ta bort om jag inte har denna hård kodning.
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID = '" + id + "'";
        db.execSQL(query);
    }
}
