package com.mohammad.criminalintentapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;


public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.mohammad.criminalintentapp.Crime_id";

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
