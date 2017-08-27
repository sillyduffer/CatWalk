package com.example.android.catwalk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CatDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "cats.db";

    private static final int DATABASE_VERSION = 1;

    public CatDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_CATS_TABLE = "CREATE TABLE " + CatContract.CatEntry.TABLE_NAME + " ("
                + CatContract.CatEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CatContract.CatEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + CatContract.CatEntry.COLUMN_ITEM_COLOUR + " TEXT NOT NULL, "
                + CatContract.CatEntry.COLUMN_ITEM_BREED + " TEXT, "
                + CatContract.CatEntry.COLUMN_ITEM_IMAGE + " TEXT NOT NULL, "
                + CatContract.CatEntry.COLUMN_ITEM_GENDER + " INTEGER NOT NULL DEFAULT 0, "
                + CatContract.CatEntry.COLUMN_ITEM_LOCATION + " TEXT);";
        db.execSQL(SQL_CREATE_CATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
