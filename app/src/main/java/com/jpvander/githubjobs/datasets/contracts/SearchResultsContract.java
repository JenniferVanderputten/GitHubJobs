package com.jpvander.githubjobs.datasets.contracts;

import android.provider.BaseColumns;

public final class SearchResultsContract {

    //Increment the version whenever modifying the schema.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SearchResults.db";
    public static final String TABLE_NAME = "search_results";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEPARATOR = ",";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + Result._ID + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR
            + Result.COLUMN_NAME_SAVED_SEARCH_ID + COMMA_SEPARATOR
            + Result.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_COMPANY + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_COMPANY_LOGO + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_COMPANY_URL + TYPE_TEXT
            +");";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    // To prevent accidental instantiation
    @SuppressWarnings("unused")
    public SearchResultsContract() {}

    public static abstract class Result implements BaseColumns {

        public static final String COLUMN_NAME_SAVED_SEARCH_ID = "saved_search_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPANY = "company";
        public static final String COLUMN_NAME_COMPANY_LOGO = "company_logo";
        public static final String COLUMN_NAME_COMPANY_URL = "company_url";
    }
}