package com.jpvander.githubjobs.datasets.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jpvander.githubjobs.datasets.contracts.SearchResultsContract;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;

public class SearchResultsDbHelper extends SQLiteOpenHelper {

    public SearchResultsDbHelper(Context context) {
        super(context, SearchResultsContract.DATABASE_NAME, null,
                SearchResultsContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SearchResultsContract.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(SearchResultsContract.SQL_DELETE_TABLE);
        onCreate(database);
    }

    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }

    public GitHubJobs getSearchResults(long savedSearchId) {
        GitHubJobs searchResults = new GitHubJobs();

        String countQuery = "SELECT * FROM " + SearchResultsContract.TABLE_NAME + " WHERE "
                + SearchResultsContract.Result.COLUMN_NAME_SAVED_SEARCH_ID + "=" + savedSearchId;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);

        while (cursor.moveToNext()) {
            if (6 < cursor.getColumnCount()) {
                GitHubJob job = new GitHubJob();
                job.setSavedSearchId(cursor.getLong(1));
                job.setTitle(cursor.getString(2));
                job.setDescription(cursor.getString(3));
                job.setCompany(cursor.getString(4));
                job.setCompany_logo(cursor.getString(5));
                job.setCompany_url(cursor.getString(6));
                searchResults.add(job);
            }
        }

        cursor.close();
        database.close();

        return searchResults;
    }

    private void insertRows(long savedSearchId, GitHubJobs jobs) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int jobIndex = 0; jobIndex < jobs.size(); jobIndex++) {
            GitHubJob job = jobs.get(jobIndex);
            ContentValues searchResultValues = new ContentValues();
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_SAVED_SEARCH_ID, savedSearchId);
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_TITLE, job.getTitle());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_DESCRIPTION, job.getDescription());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY, job.getCompany());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_LOGO, job.getCompany_logo());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_URL, job.getCompany_url());
            database.insert(SearchResultsContract.TABLE_NAME, null, searchResultValues);
        }

        database.close();
    }

    public void saveSearchResults(long savedSearchId, GitHubJobs jobs) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteOldItems = "DELETE FROM " + SearchResultsContract.TABLE_NAME + " WHERE "
                + SearchResultsContract.Result.COLUMN_NAME_SAVED_SEARCH_ID + " = " + savedSearchId;
        database.execSQL(deleteOldItems);
        database.close();
        insertRows(savedSearchId, jobs);
    }
}
