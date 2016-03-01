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
            searchResults.add(getJobFromRow(cursor));
        }

        cursor.close();
        database.close();

        return searchResults;
    }

    public GitHubJob getJob(String jobId) {
        String countQuery = "SELECT * FROM " + SearchResultsContract.TABLE_NAME + " WHERE "
                + SearchResultsContract.Result.COLUMN_NAME_JOB_ID + "='" + jobId + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        GitHubJob job = getJobFromRow(cursor);
        cursor.close();
        database.close();
        return job;
    }

    private GitHubJob getJobFromRow(Cursor cursor) {
        //TODO: Maybe an enum would make this (and other similar blocks) more elegant and extensible?
        GitHubJob job = new GitHubJob();
        int columnCount = cursor.getColumnCount();
        int columnIndex = 0;
        if (columnIndex++ < columnCount) { job.setSavedSearchId(cursor.getLong(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setId(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setCompany(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setLocation(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setTitle(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setCompany_url(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setDescription(cursor.getString(columnIndex)); }
        if (columnIndex++ < columnCount) { job.setCompany_logo(cursor.getString(columnIndex)); }
        return job;
    }

    private void insertRows(long savedSearchId, GitHubJobs jobs) {
        SQLiteDatabase database = this.getWritableDatabase();

        for (int jobIndex = 0; jobIndex < jobs.size(); jobIndex++) {
            GitHubJob job = jobs.get(jobIndex);
            ContentValues searchResultValues = new ContentValues();
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_SAVED_SEARCH_ID, savedSearchId);
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_JOB_ID, job.getId());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY, job.getCompany());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_LOCATION, job.getLocation());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_TITLE, job.getTitle());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_DESCRIPTION, job.getDescription());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_URL, job.getCompany_url());
            searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_LOGO, job.getCompany_logo());
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
