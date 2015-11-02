package com.mohammad.criminalintentapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mohammad.criminalintentapp.Crime;
import com.mohammad.criminalintentapp.database.CrimeDbSchema.CrimeTable;
import com.mohammad.criminalintentapp.database.CrimeDbSchema.CrimeTable.Cols;

import java.sql.Connection;

/**
 * Created by user on 10/28/2015.
 */
public class CrimeDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimes.db";

    public CrimeDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ CrimeTable.NAME+"(_id integer primary key autoincrement, "+
                Cols.UUID+ ","  +
                Cols.TITLE + ","+
                Cols.DATE+","+
                Cols.SOLVED +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
