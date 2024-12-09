package com.example.to_do_list_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dataBase extends SQLiteOpenHelper {

    public static final String TO_DO_LIST_TABLE = "TO_DO_LIST_TABLE";
    public static final String TITLE = "title";
    public static final String ID = TITLE + "Id";
    public static final String DAY = "day";
    public static final String DESCRIPTION = "description";
    public static final String IS_COMPLETED = "isCompleted";
    private static final String USERNAME ="userName" ;
    private static final String COLUMN_IMAGE = "image";

    public dataBase(@Nullable Context context) {
        super(context, "todoList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= " CREATE TABLE " + TO_DO_LIST_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " Text, " + DAY + " TEXT," + DESCRIPTION + " TEXT, " + IS_COMPLETED + " BOOL, " +USERNAME + " TEXT, "+ COLUMN_IMAGE+ " BLOB)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(dataModelClass datamodel)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, datamodel.getTitle());
        cv.put(DAY,datamodel.getDay());
        cv.put(IS_COMPLETED, datamodel.isCompleted());
        cv.put(DESCRIPTION,datamodel.getDetail());
        long insert = db.insert(TO_DO_LIST_TABLE, null, cv);
        if(insert==-1)
            return false;
        return  true;
    }

    public   String retunUserName()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;
        String selectQuery = "SELECT *  FROM " + TO_DO_LIST_TABLE;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
        }
        cursor.close();
        db.close();
        return username;

    }
    public void addUser(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();// static username
         values.put(USERNAME, userName);
         db.insert(TO_DO_LIST_TABLE, null, values);
         db.close();
    }


    public ArrayList<String> returnTitles(String day) {
        ArrayList<String> titlesList = new ArrayList<>();

        // Properly format the query string
        String queryString = "SELECT " + TITLE + " FROM " + TO_DO_LIST_TABLE + " WHERE "+IS_COMPLETED+" = 0 AND " + DAY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{day});
        if (cursor != null && cursor.moveToFirst()) {
            // Loop through the table and add titles to the list
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                if(title.isEmpty())
                    continue;
                titlesList.add(title);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return titlesList;
    }

    public void setCompleted(String title, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + TO_DO_LIST_TABLE + " SET " + IS_COMPLETED + " = ? WHERE " + TITLE + " = ? AND " + DAY + " = ?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_COMPLETED, true);
        db.execSQL(queryString, new Object[]{true, title, day});
        db.close();
    }

    public String titleDetails(String title, String day) {
        String detail = null;
        String queryString = "SELECT " + DESCRIPTION + " FROM " + TO_DO_LIST_TABLE + " WHERE " + DAY + " = ? AND " + IS_COMPLETED + " = 0 AND " + TITLE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{day, title});
        if (cursor.moveToFirst()) {
            detail = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
        }
        cursor.close();
        db.close();
        return detail;
    }



    public ArrayList<String> searchResult(String searchInput, String day) {
        ArrayList<String> titlesList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TO_DO_LIST_TABLE + " WHERE " + DAY + " = ? AND " + IS_COMPLETED + " = 0 AND " + TITLE + " LIKE ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{day, "%" + searchInput + "%"});
        if (cursor != null && cursor.moveToFirst()) {
            // Loop through the table and add titles to the list
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                titlesList.add(title);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return titlesList;
    }



}
