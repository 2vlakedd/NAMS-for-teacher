package com.forappnams.nfcattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private static final String TABLE_NAME = "Students";
    private static final String TABLE_NAME1 = "Attendance";
    private static final String COL1 = "ID";
    private static final String COL3 = "STUDID";
    private static final String COL2 = "STUDENTNAME";
    private static final String COL4 = "ATTENDANCE";
    private static final String COL5 = "DATE";
    private static final String COL6 = "TIME";




    public DatabaseHelper(Context context) {
        super(context,TABLE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable ="CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,STUDID TEXT,STUDENTNAME TEXT)";
        String createTable1 ="CREATE TABLE " + TABLE_NAME1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,STUDENTNAME TEXT,TIME TEXT)";

        db.execSQL(createTable);
        db.execSQL(createTable1);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1 );
        onCreate(db);

    }
    public boolean insertAttend(String date,String name,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL5, date);
        contentValues.put(COL2, name);
        contentValues.put(COL6, time);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        if (result == 1) {
            return false;
        }else {
            return true;
        }

    }
    public boolean insertData(String id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, name);




        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == 1) {
            return false;
        }else {
            return true;
        }
    }
    public Cursor getData(){

        SQLiteDatabase db =this.getWritableDatabase();

        String query = " SELECT * FROM " + TABLE_NAME;
        Cursor data =db.rawQuery(query,null);
        return data;
    }



    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME+
                " WHERE " + COL3 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAttendData(){

        SQLiteDatabase db =this.getWritableDatabase();

        String query = " SELECT * FROM " + TABLE_NAME1;
        Cursor data =db.rawQuery(query,null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }


    public void  deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}