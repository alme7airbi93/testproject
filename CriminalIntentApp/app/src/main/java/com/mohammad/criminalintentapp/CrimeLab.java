package com.mohammad.criminalintentapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mohammad.criminalintentapp.database.CrimeCursorWraper;
import com.mohammad.criminalintentapp.database.CrimeDbHelper;
import com.mohammad.criminalintentapp.database.CrimeDbSchema;
import com.mohammad.criminalintentapp.database.CrimeDbSchema.CrimeTable;
import com.mohammad.criminalintentapp.database.CrimeDbSchema.CrimeTable.Cols;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab
{
    private static CrimeLab sCrimeLab;
    private Context context;
    private SQLiteDatabase database;


    public CrimeLab(Context context)
    {
        this.context = context.getApplicationContext();
        database = new CrimeDbHelper(this.context).getWritableDatabase();
    }

    public List<Crime> getCrimes()
    {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWraper crimeCursorWraper = querryCrimes(null, null);
        try
        {
            crimeCursorWraper.moveToFirst();
            while (!crimeCursorWraper.isAfterLast())
            {
                crimes.add(crimeCursorWraper.getCrime());
                crimeCursorWraper.moveToNext();
            }

        }finally {
            crimeCursorWraper.close();

        }

        return crimes;
    }

    public Crime getCrime(UUID id)
    {
        CrimeCursorWraper cursorWraper =
                querryCrimes(Cols.UUID +"=?",new String[]{id.toString()});
        try
        {
            if(cursorWraper.getCount() == 0)
            {
                return null;
            }

            cursorWraper.moveToFirst();
            return cursorWraper.getCrime();
        }
        finally {
            cursorWraper.close();
        }
    }


    public void addCrime(Crime crime)
    {
        ContentValues values = getContentValues(crime);
        database.insert(CrimeTable.NAME, null , values);
    }



    public static CrimeLab getsCrimeLab(Context context)
    {
        if(sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private static ContentValues getContentValues(Crime crime)
    {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID,crime.getId().toString() );
        values.put(Cols.TITLE,crime.getTitle().toString() );
        values.put(Cols.DATE,crime.getDate().getTime());
        values.put(Cols.SOLVED,crime.isSolved() ? 1 : 0);

        return values;
    }

    private CrimeCursorWraper querryCrimes(String whereClause,String [] whereArgs)
    {
        Cursor cursor = database.query(
                CrimeTable.NAME,
                null, // all Cols
                whereClause,
                whereArgs,
                null, //groupBY
                null, // having
                null  // orderBy
        );
        return new CrimeCursorWraper(cursor);
    }

    public void updateCrime(Crime crime)
    {
        UUID id = crime.getId();
        ContentValues values = getContentValues(crime);

        database.update(CrimeTable.NAME, values, Cols.UUID +" = ?",new String[] {id.toString()});
    }







}
