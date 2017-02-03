package com.andrew.niku.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by niku on 06.12.2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();

    }
}
