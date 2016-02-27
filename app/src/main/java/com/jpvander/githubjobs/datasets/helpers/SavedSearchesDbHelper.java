package com.jpvander.githubjobs.datasets.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.jpvander.githubjobs.datasets.contracts.SavedSearchesContract;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;

public class SavedSearchesDbHelper extends SQLiteOpenHelper {

    public SavedSearchesDbHelper(Context context) {
        super(context, SavedSearchesContract.DATABASE_NAME, null,
                SavedSearchesContract.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SavedSearchesContract.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(SavedSearchesContract.SQL_DELETE_TABLE);
        onCreate(database);
    }

    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }

    public void insertRow(GitHubJob job) {
        ContentValues savedSearchValues = new ContentValues();
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_LOCATION, job.getLocation());
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_DESCRIPTION, job.getDescription());

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(SavedSearchesContract.TABLE_NAME, null, savedSearchValues);
        database.close();
    }

    public GitHubJobs getSavedSearches() {
        GitHubJobs savedSearches = new GitHubJobs();

        String countQuery = "SELECT * FROM " + SavedSearchesContract.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);

        while (cursor.moveToNext()) {
            if (3 >= cursor.getColumnCount()) {
                savedSearches.add(new GitHubJob(cursor.getString(2), cursor.getString(1)));
            }
        }

        cursor.close();
        database.close();

        return savedSearches;
    }

    public long getRowCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        long rowCount  = DatabaseUtils.queryNumEntries(database, SavedSearchesContract.TABLE_NAME);
        database.close();
        return rowCount;
    }
}
