package com.andrew.niku.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;


/**
 * Created by niku on 06.12.2016.
 */

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";
    private static final String EXTRA_CRIME_ID = "com.andrew.niku.criminalintent.crime_id";
    private static int REQUEST_CRIME = 1;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private boolean mIsSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mIsSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;

    }

    private void updateUI() {

        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimes(crimes);
            mCrimeAdapter.notifyDataSetChanged();
        }

        updateSubTitle();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == REQUEST_CRIME) {
            if (data.getSerializableExtra() == ...) {

            }
        } */
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);

            mCrimeCheckboxSolved =
                    (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mCrimeTitleTextView =
                    (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            mCrimeDateTextView =
                    (TextView) itemView.findViewById(R.id.list_item_date_text_view);

            itemView.setOnClickListener(this);

        }

        public CheckBox mCrimeCheckboxSolved;
        public TextView mCrimeTitleTextView;
        public TextView mCrimeDateTextView;

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mCrimeCheckboxSolved.setChecked(crime.isSolved());
            mCrimeTitleTextView.setText(crime.getTitle());
            mCrimeDateTextView.setText(crime.getDate().toString());
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),
//                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
//            .show();

//            Intent intent = new Intent(getActivity(), CrimeActivity.class);

//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
//            //startActivity(intent);
//            startActivityForResult(intent, REQUEST_CRIME);

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);

        }
    }

    public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemCount() {
            //Log.d(TAG, "getItemCount() call. size = " + mCrimes.size());
            //Logger.withTag(TAG).log("getItemCount() call. size = " + mCrimes.size()).log("---");
            return mCrimes.size();
        }

        public void setCrimes(List crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //Log.d(TAG, "onCreateViewHolder() called. parent = " + parent);
            //Logger.withTag(TAG).log("onCreateViewHolder() called. parent = " + parent).log("---");

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            //Log.d(TAG, "onBindViewHolder() called. position = " + position);
            //Logger.withTag(TAG).log("onBindViewHolder() called. position = " + position);

            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);

        }

    }

    @Override
    public void onResume() {
        Logger.withTag(TAG).log("onResume() called");
        super.onResume();
        updateUI();
    }

    public static Intent getIntentForResult(Context packageContext, UUID crimeID) {
        Intent intent = new Intent(packageContext, CrimeListFragment.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);

        return intent;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem menuItemSubtitle = menu.findItem(R.id.menu_item_show_subtitle);

        if (menuItemSubtitle != null) {
            Logger.withTag(TAG).log("onCreateOptionsMenu() called. mIsSubtitleVisible = " + mIsSubtitleVisible);
            if (mIsSubtitleVisible) {
                menuItemSubtitle.setTitle(R.string.hide_subtitle);
            } else {
                menuItemSubtitle.setTitle(R.string.show_subtitle);
            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);

                return true;

            case R.id.menu_item_show_subtitle:

                getActivity().invalidateOptionsMenu();
                updateSubTitle();
                mIsSubtitleVisible = !mIsSubtitleVisible;
                return true;

            case R.id.menu_item_options:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateSubTitle() {

        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();

        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount);

        Logger.withTag("updateSubTitle() called. mIsSubtitleVisible = " + mIsSubtitleVisible);
        if (mIsSubtitleVisible) {
            subtitle = null;
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mIsSubtitleVisible);
    }
}
