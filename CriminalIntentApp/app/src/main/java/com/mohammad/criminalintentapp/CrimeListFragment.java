package com.mohammad.criminalintentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 10/14/2015.
 */
public class CrimeListFragment extends Fragment
{
    private RecyclerView recyclerView;
    private CrimeAdapter crimeAdapter;

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.crime_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.getsCrimeLab(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(), CrimeActivity.class);
                i.putExtra(CrimeActivity.EXTRA_CRIME_ID, crime.getId());
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateUI()
    {
        CrimeLab crimeLab = CrimeLab.getsCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(crimes);
            recyclerView.setAdapter(crimeAdapter);
        }
        else {
            crimeAdapter.setCrimes(crimes);
            crimeAdapter.notifyDataSetChanged();
        }
    }
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        // Variables
        private TextView tvCrimeTitle;
        private TextView tvCrimeDate;
        private CheckBox cbCrimeSolved;

        private Crime crime;

        // Constracter
        public CrimeHolder(View view)
        {
            super(view);

            view.setOnClickListener(this);

            tvCrimeTitle = (TextView)view.findViewById(R.id.tvListCrimeTitle);
            tvCrimeDate = (TextView)view.findViewById(R.id.tvListDate);
            cbCrimeSolved = (CheckBox)view.findViewById(R.id.cbListCrimeSolved);
            cbCrimeSolved.setClickable(false);
        }

        //
        public void bindCrime(Crime crime)
        {
            this.crime = crime;
            tvCrimeTitle.setText(crime.getTitle());
            tvCrimeDate.setText(crime.getDate().toString());
            cbCrimeSolved.setChecked(crime.isSolved());


        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getActivity(),CrimeActivity.class);
            i.putExtra(CrimeActivity.EXTRA_CRIME_ID, crime.getId());
            startActivity(i);

        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Crime> crimes;

        public CrimeAdapter (List<Crime> crimes)
        {
            this.crimes = crimes;
        }
        @Override
        public int getItemCount() {
            return crimes.size();
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

//            View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup,false);
            View v = inflater.inflate(R.layout.list_item_crime, viewGroup, false);



            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder crimeHolder, int i)
        {
            Crime crime = crimes.get(i);
            crimeHolder.bindCrime(crime);
        }
        public void setCrimes(List<Crime> crimes)
        {
            this.crimes = crimes;
        }
    }
}


