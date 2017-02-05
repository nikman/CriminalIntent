package com.andrew.niku.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by niku on 06.12.2016.
 */

public class CrimeListActivity
        extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();

    }

    @Override
    protected int getLayoutResId() {
        //return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {

        if (findViewById(R.id.detail_fragmentContainer) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }else{
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragmentContainer, newDetail)
                    .commit();
        }

    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }
}
