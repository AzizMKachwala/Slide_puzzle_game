package com.example.slidepuzzlegame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHandler extends SQLiteOpenHelper {

    //USER TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Swipe_Puzzle";
    private static final String TABLE_USERS = "Users";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_IMAGE = "User_Image";

    //SCORE TABLE
    private static final String TABLE_SCORE = "Scores";
    private static final String KEY_SCORE_ID = "Id";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_MOVES = "Moves";
    private static final String KEY_TIME = "Time_Taken";


    public MyDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_SCORE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_SCORE_ID + " INTEGER PRIMARY KEY,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_MOVES + " INTEGER,"
                + KEY_TIME + " INTEGER,"
                + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(String Name, String Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name);
        values.put(KEY_IMAGE, Image);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void insertScore(int userId, int moves, int timeTaken) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_MOVES, moves);
        values.put(KEY_TIME, timeTaken);
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    public ArrayList<MyDatabaseScoreModel> getAllScores() {
        ArrayList<MyDatabaseScoreModel> scoreList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_SCORE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int scoreId = cursor.getInt(cursor.getColumnIndex(KEY_SCORE_ID));
                @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                @SuppressLint("Range") int moves = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range") int timeTaken = cursor.getInt(cursor.getColumnIndex(KEY_TIME));

                MyDatabaseScoreModel score = new MyDatabaseScoreModel(scoreId, userId, moves, timeTaken);
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return scoreList;
    }

    public ArrayList<MyDatabaseScoreModel> getUserHistory(int userId) {
        ArrayList<MyDatabaseScoreModel> userHistoryList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_SCORE + " WHERE " + KEY_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int scoreId = cursor.getInt(cursor.getColumnIndex(KEY_SCORE_ID));
                @SuppressLint("Range") int moves = cursor.getInt(cursor.getColumnIndex(KEY_MOVES));
                @SuppressLint("Range") int timeTaken = cursor.getInt(cursor.getColumnIndex(KEY_TIME));

                MyDatabaseScoreModel userHistory = new MyDatabaseScoreModel(scoreId, userId, moves, timeTaken);
                userHistoryList.add(userHistory);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userHistoryList;
    }

    public ArrayList<MyDatabaseModel> getAllUsers() {
        ArrayList<MyDatabaseModel> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));

                MyDatabaseModel user = new MyDatabaseModel(id, name, image);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
