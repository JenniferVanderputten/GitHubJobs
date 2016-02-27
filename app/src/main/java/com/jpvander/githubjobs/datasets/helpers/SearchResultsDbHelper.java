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

    public GitHubJobs getSearchResults() {
        GitHubJobs searchResults = new GitHubJobs();

        String countQuery = "SELECT * FROM " + SearchResultsContract.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);

        while (cursor.moveToNext()) {
            if (6 >= cursor.getColumnCount()) {
                GitHubJob job = new GitHubJob();
                job.setTitle(cursor.getString(1));
                job.setDescription(cursor.getString(2));
                job.setCompany(cursor.getString(3));
                job.setCompany_logo(cursor.getString(4));
                job.setCompany_url(cursor.getString(5));
                searchResults.add(job);
            }
        }

        cursor.close();
        database.close();

        return searchResults;
    }

    private void insertRow(GitHubJob job) {
        ContentValues searchResultValues = new ContentValues();
        searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_TITLE, job.getTitle());
        searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_DESCRIPTION, job.getDescription());
        searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY, job.getCompany());
        searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_LOGO, job.getCompany_logo());
        searchResultValues.put(SearchResultsContract.Result.COLUMN_NAME_COMPANY_URL, job.getCompany_url());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(SearchResultsContract.TABLE_NAME, null, searchResultValues);
        database.close();
    }

    public void saveSearchResults(GitHubJobs jobs) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(SearchResultsContract.SQL_DELETE_TABLE);
        database.execSQL(SearchResultsContract.SQL_CREATE_TABLE);
        database.close();

        for (int jobIndex = 0; jobIndex < jobs.size(); jobIndex++) {
            insertRow(jobs.get(jobIndex));
        }
    }
}
