package com.mohammad.criminalintentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment
{
    private Crime crime;
    private EditText etCrimeTitle;
    private Button btnCrimeDate;
    private CheckBox cbCrimeSolved;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeID = (UUID)getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);


        crime = CrimeLab.getsCrimeLab(getActivity()).getCrime(crimeID);


    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getsCrimeLab(getActivity()).updateCrime(crime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container,false);

        etCrimeTitle = (EditText)v.findViewById(R.id.etCrimetitle);
        etCrimeTitle.setText(crime.getTitle());
        etCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no code
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no code
            }
        });
        btnCrimeDate = (Button)v.findViewById(R.id.btnCrimeDate);
        btnCrimeDate.setText(crime.getDate().toString());
        // btnCrimeDate.setEnabled(false);
        btnCrimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DatePickerFragment.ARG_DATE, crime.getDate());
                datePickerFragment.setArguments(bundle);
                datePickerFragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE);

                datePickerFragment.show(fm,DIALOG_DATE);

            }
        });
        cbCrimeSolved = (CheckBox)v.findViewById(R.id.cbCrimeSolved);
        cbCrimeSolved.setChecked(crime.isSolved());
        cbCrimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }

        if(requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            btnCrimeDate.setText(crime.getDate().toString());
        }
    }
}
