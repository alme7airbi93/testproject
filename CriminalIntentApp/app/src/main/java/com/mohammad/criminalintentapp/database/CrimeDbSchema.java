package com.mohammad.criminalintentapp.database;

/**
 * Created by user on 10/28/2015.
 */
public class CrimeDbSchema {

    public static final class CrimeTable
    {
        public static final String NAME = "crime";
        public static final class Cols
        {
            public static final String UUID = "uuid";
            public static final String TITLE = "TITLE";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
