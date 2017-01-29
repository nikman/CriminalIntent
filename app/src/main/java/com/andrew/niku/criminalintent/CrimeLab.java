package com.andrew.niku.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andrew.niku.criminalintent.database.CrimeBaseHelper;
import com.andrew.niku.criminalintent.database.CrimeCursorWrapper;
import com.andrew.niku.criminalintent.database.CrimeDbSchema;
import com.andrew.niku.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by niku on 06.12.2016.
 */

public class CrimeLab {

    private static CrimeLab sInstance;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    static CrimeLab getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new CrimeLab(context);
        }
        return sInstance;
    }

    private CrimeLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }

        return crimes;

    }

    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor =
                queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[] {id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }

    public void addCrime(Crime crime) {
        ContentValues contentValues = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    public void updateCrime(Crime crime) {

        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(
                CrimeTable.NAME,
                values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString}
        );

    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {

        Cursor cursor = mDatabase.query(CrimeTable.NAME,
                null, // select all cols
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeCursorWrapper(cursor);

    }

    private static ContentValues getContentValues(Crime crime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        contentValues.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return contentValues;

    }

}
