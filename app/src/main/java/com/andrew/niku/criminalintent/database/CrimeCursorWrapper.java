package com.andrew.niku.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andrew.niku.criminalintent.Crime;
import com.andrew.niku.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.UUID;

/**
 * Created by niku on 28.01.2017.
 */

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {

        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new java.sql.Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;

    }

}
