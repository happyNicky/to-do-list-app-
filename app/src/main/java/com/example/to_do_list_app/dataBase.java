package com.example.to_do_list_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class dataBase extends SQLiteOpenHelper {

    public static final String TO_DO_LIST_TABLE = "TO_DO_LIST_TABLE";
    public static final String TITLE = "title";
    public static final String ID = TITLE + "Id";
    public static final String DAY = "day";
    public static final String DESCRIPTION = "description";
    public static final String IS_COMPLETED = "isCompleted";
    private static final String USERNAME = "userName";
    private static final String COLUMN_IMAGE = "image";
    private static final String IMAGE_IS_CHOOSEN = "imageIschoosen";
    private static final String USER_INFO = "USER_INFO_TABLE";

    public dataBase(@Nullable Context context) {
        super(context, "todoList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement1 = "CREATE TABLE " + TO_DO_LIST_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + DAY + " TEXT, " + DESCRIPTION + " TEXT, " + IS_COMPLETED + " BOOL, " + USERNAME + " TEXT)";
        String createTableStatement2 = "CREATE TABLE " + USER_INFO + " (" + COLUMN_IMAGE + " TEXT, " + IMAGE_IS_CHOOSEN + " BOOL)";
        db.execSQL(createTableStatement1);
        db.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertOrUpdateImage(Context context, Uri imageUri){ SQLiteDatabase db = this.getWritableDatabase();
        String imagePath = null;
        try {
             InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
             byte[] imageBytes = getBytes(inputStream);
             imagePath = saveImageToInternalStorage(context, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
     ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_IMAGE, imagePath);
    contentValues.put(IMAGE_IS_CHOOSEN, 1);
     Cursor cursor = db.rawQuery("SELECT * FROM " + USER_INFO, null);
     if (cursor.moveToFirst()) {
        db.update(USER_INFO, contentValues, null, null);
     } else {
        db.insert(USER_INFO, null, contentValues);
     }
     cursor.close();
     db.close();
    }

    public Uri getImage(Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Uri imageUri = null;

        String selectQuery = "SELECT * FROM " + USER_INFO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(0)) {
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            File imageFile = new File(imagePath);
            imageUri = Uri.fromFile(imageFile);
        }
        cursor.close();
        db.close();
        return imageUri;
    }

    // Helper method to convert InputStream to byte array
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    // Save image to internal storage and return the file path
    private String saveImageToInternalStorage(Context context, byte[] imageBytes) throws IOException {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, "image_" + System.currentTimeMillis() + ".jpg");
        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(imageBytes);
        fos.close();
        return imageFile.getAbsolutePath();
    }

    public boolean addOne(dataModelClass datamodel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, datamodel.getTitle());
        cv.put(DAY, datamodel.getDay());
        cv.put(IS_COMPLETED, datamodel.isCompleted());
        cv.put(DESCRIPTION, datamodel.getDetail());
        long insert = db.insert(TO_DO_LIST_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public String retunUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = null;
        String selectQuery = "SELECT * FROM " + TO_DO_LIST_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
        }
        cursor.close();
        db.close();
        return username;
    }

    public boolean returnIsImageChoosen() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isImageChoosen = false;
        String selectQuery = "SELECT * FROM " + USER_INFO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int imageIsChosenColumnIndex = cursor.getColumnIndexOrThrow(IMAGE_IS_CHOOSEN);
            isImageChoosen = cursor.getInt(imageIsChosenColumnIndex) > 0;
        }
        cursor.close();
        db.close();
        return isImageChoosen;
    }

    public void addUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, userName);
        db.insert(TO_DO_LIST_TABLE, null, values);
        db.close();
    }

    public ArrayList<String> returnTitles(String day) {
        ArrayList<String> titlesList = new ArrayList<>();
        String queryString = "SELECT " + TITLE + " FROM " + TO_DO_LIST_TABLE + " WHERE " + IS_COMPLETED + " = 0 AND " + DAY + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{day});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                if (!title.isEmpty()) {
                    titlesList.add(title);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return titlesList;
    }

    public void setCompleted(String title, String day) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + TO_DO_LIST_TABLE + " SET " + IS_COMPLETED + " = ? WHERE " + TITLE + " = ? AND " + DAY + " = ?";
        db.execSQL(queryString, new Object[]{true, title, day});
        db.close();
    }

    public boolean isSelected() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isSelectedImage = false;
        String selectQuery = "SELECT * FROM " + USER_INFO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToPosition(0)) {

            int booleanColumnIndex = cursor.getColumnIndexOrThrow(IMAGE_IS_CHOOSEN);
            isSelectedImage = cursor.getInt(booleanColumnIndex) > 0;
        } cursor.close();
        db.close();
        return isSelectedImage;
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
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                titlesList.add(title);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return titlesList;
    }

    private byte[] getBytesFromUri(Context context, Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return getBytes(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
