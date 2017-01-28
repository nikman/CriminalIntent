package com.andrew.niku.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.andrew.niku.criminalintent.database.CrimeBaseHelper;
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

    //private List<Crime> mCrimes;
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
        //mCrimes = new ArrayList<>();

    }

    public List<Crime> getCrimes() {
        //return mCrimes;
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id) {

        /*for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return  crime;
            }
        }*/

        return null;

    }

    public void addCrime(Crime crime) {
        //mCrimes.add(crime);
        ContentValues contentValues = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    private static ContentValues getContentValues(Crime crime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return contentValues;

    }

}
