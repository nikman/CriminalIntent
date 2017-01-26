package com.andrew.niku.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by niku on 06.12.2016.
 */
public class CrimeLab {

    private static CrimeLab sInstance;

    private List<Crime> mCrimes;

    static CrimeLab getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new CrimeLab(context);
        }
        return sInstance;
    }

    private CrimeLab(Context context) {
//        mAppContext = appContext;
        mCrimes = new ArrayList<>();
/*
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }*/
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {

        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return  crime;
            }
        }

        return null;

    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

}
