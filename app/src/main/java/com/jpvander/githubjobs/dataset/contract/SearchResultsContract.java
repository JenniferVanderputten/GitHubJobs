package com.jpvander.githubjobs.dataset.contract;

import android.annotation.SuppressLint;
import android.provider.BaseColumns;

import com.jpvander.githubjobs.dataset.data.GitHubJob;

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
            + Result.COLUMN_NAME_SAVED_SEARCH_ID + TYPE_INTEGER + COMMA_SEPARATOR
            + Result.COLUMN_NAME_JOB_ID + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_COMPANY + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_LOCATION + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_WEBSITE + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEPARATOR
            + Result.COLUMN_NAME_LOGO_URL + TYPE_TEXT
            +");";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SQL_SELECT_ALL_WHERE = "SELECT * FROM " + TABLE_NAME + " WHERE ";
    public static final String SQL_DELETE_ALL_WHERE = "DELETE FROM " + TABLE_NAME + " WHERE ";

    // To prevent accidental instantiation
    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public SearchResultsContract() {}

    public static abstract class Result implements BaseColumns {

        public static final String COLUMN_NAME_SAVED_SEARCH_ID = GitHubJob.SAVED_SEARCH_ID_LABEL;
        public static final String COLUMN_NAME_JOB_ID = GitHubJob.ID_LABEL;
        public static final String COLUMN_NAME_COMPANY = GitHubJob.COMPANY_LABEL;
        public static final String COLUMN_NAME_LOCATION = GitHubJob.LOCATION_LABEL;
        public static final String COLUMN_NAME_TITLE = GitHubJob.TITLE_LABEL;
        public static final String COLUMN_NAME_WEBSITE = GitHubJob.WEBSITE_LABEL;
        public static final String COLUMN_NAME_DESCRIPTION = GitHubJob.DESCRIPTION_LABEL;
        public static final String COLUMN_NAME_LOGO_URL = GitHubJob.LOGO_URL_LABEL;
    }
}