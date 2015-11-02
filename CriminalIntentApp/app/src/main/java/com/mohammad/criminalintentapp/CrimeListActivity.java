package com.mohammad.criminalintentapp;

import android.support.v4.app.Fragment;

/**
 * Created by user on 10/14/2015.
 */
public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
