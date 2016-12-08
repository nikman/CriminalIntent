package com.andrew.niku.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by niku on 06.12.2016.
 */

public class CrimeListFragment extends Fragment {

    private static final String TAG = "CrimeListFragment";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;

    }

    private void updateUI() {

        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mCrimeAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mCrimeAdapter);

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        public CheckBox mCrimeCheckboxSolved;
        public TextView mCrimeTitleTextView;
        public TextView mCrimeDateTextView;

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mCrimeCheckboxSolved.setChecked(crime.isSolved());
            mCrimeTitleTextView.setText(crime.getTitle());
            mCrimeDateTextView.setText(crime.getDate().toString());
        }

        public CrimeHolder(View itemView) {
            super(itemView);

            mCrimeCheckboxSolved = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
            mCrimeDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_text_view);

            itemView.setOnClickListener(this);

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

            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
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
            Log.d(TAG, "getItemCount() call. size = " + mCrimes.size());
            return mCrimes.size();
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Log.d(TAG, "onCreateViewHolder() called. parent = " + parent);

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            Log.d(TAG, "onBindViewHolder() called. position = " + position);

            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);

        }

    }

}
