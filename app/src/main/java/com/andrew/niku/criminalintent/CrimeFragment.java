package com.andrew.niku.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.github.skydoves.ElasticButton;

import java.util.Date;
import java.util.UUID;

/**
 * Created by niku on 01.12.2016.
 */

public class CrimeFragment extends Fragment {

    private final String TAG = this.getTag();

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleEditField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();
        /*UUID crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeID);*/
        Bundle args = getArguments();
        UUID crimeId = (UUID) args.getSerializable(ARG_CRIME_ID);

        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }

    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleEditField = (EditText) view.findViewById(R.id.crime_title);
        mTitleEditField.setText(mCrime.getTitle());
        mTitleEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, charSequence.toString());
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, editable.toString());
            }
        });

        mDateButton = (ElasticButton) view.findViewById(R.id.crime_date_button);
        updateDate();
        if (mDateButton != null) {
            mDateButton.setText(
                    DateFormat.format("EEE, d MMM yyyy HH:mm", mCrime.getDate()).toString());
            //mDateButton.setEnabled(false);
            mDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    DatePickerFragment datePickerDialog =
                            DatePickerFragment.newInstance(mCrime.getDate());
                    datePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                    datePickerDialog.show(fragmentManager, DIALOG_DATE);
                }
            });
        }

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved_check_box);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        if (mSolvedCheckBox != null){
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCrime.setSolved(b);
                }
            });
        }

        return view;
    }

    public static CrimeFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;

    }

    public void returnResult() {
        Intent intent = CrimeListFragment.getIntentForResult(getActivity(), mCrime.getId());
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

}
