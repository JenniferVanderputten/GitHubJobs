package com.jpvander.githubjobs.dataset.contract;

import android.annotation.SuppressLint;
import android.provider.BaseColumns;

import com.jpvander.githubjobs.dataset.data.GitHubJob;

public final class SavedSearchesContract {

    //Increment the version whenever modifying the schema.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SavedSearches.db";
    public static final String TABLE_NAME = "saved_searches";
    private static final String TYPE_TEXT = " TEXT";
    private static final String COMMA_SEPARATOR = ",";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + SavedSearchesContract.TABLE_NAME + " ("
            + Search._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR
            + Search.COLUMN_NAME_LOCATION + TYPE_TEXT + COMMA_SEPARATOR
            + Search.COLUMN_NAME_DESCRIPTION + TYPE_TEXT
            +");";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    // To prevent accidental instantiation
    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public SavedSearchesContract() {}

    public static abstract class Search implements BaseColumns {

        public static final String COLUMN_NAME_LOCATION = GitHubJob.LOCATION_LABEL;
        public static final String COLUMN_NAME_DESCRIPTION = GitHubJob.DESCRIPTION_LABEL;
    }
}