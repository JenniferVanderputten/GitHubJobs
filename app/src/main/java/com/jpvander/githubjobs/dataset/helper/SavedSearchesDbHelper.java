package com.jpvander.githubjobs.dataset.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.jpvander.githubjobs.dataset.contract.SavedSearchesContract;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;

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

    public void deleteRow(long savedSearchId) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteRecord = SavedSearchesContract.SQL_DELETE_RECORD
                + SavedSearchesContract.Search._ID + " = " + savedSearchId;
        database.execSQL(deleteRecord);
        database.close();
    }

    public boolean insertRow(GitHubJob job) {
        SQLiteDatabase database = this.getReadableDatabase();
        String findRecords = SavedSearchesContract.SQL_SELECT_WHERE
                + sqlEquality(SavedSearchesContract.Search.COLUMN_NAME_LOCATION, job.getLocation()) + " AND "
                + sqlEquality(SavedSearchesContract.Search.COLUMN_NAME_DESCRIPTION, job.getDescription()) + " AND "
                + sqlEquality(SavedSearchesContract.Search.COLUMN_NAME_FULL_TIME, job.isFullTime().toString()) + " AND "
                + sqlEquality(SavedSearchesContract.Search.COLUMN_NAME_PART_TIME, job.isPartTime().toString());
        Cursor cursor = database.rawQuery(findRecords, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            database.close();
            return false;
        }
        else {
            database.close();
        }

        ContentValues savedSearchValues = new ContentValues();
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_LOCATION, job.getLocation());
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_DESCRIPTION, job.getDescription());
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_FULL_TIME, job.isFullTime().toString());
        savedSearchValues.put(SavedSearchesContract.Search.COLUMN_NAME_PART_TIME, job.isPartTime().toString());

        database = this.getWritableDatabase();
        long id = database.insert(SavedSearchesContract.TABLE_NAME, null, savedSearchValues);
        database.close();
        job.setSavedSearchId(id);
        return true;
    }

    public GitHubJobs getSavedSearches() {
        GitHubJobs savedSearches = new GitHubJobs();

        String countQuery = SavedSearchesContract.SQL_SELECT_ALL;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);

        while (cursor.moveToNext()) {
            GitHubJob job = new GitHubJob();
            Boolean fullTime = false;
            Boolean partTime = false;

            int columnCount = cursor.getColumnCount();
            int columnIndex = -1;
            if (columnIndex++ < columnCount) { job.setSavedSearchId(cursor.getLong(columnIndex)); }
            if (columnIndex++ < columnCount) { job.setLocation(cursor.getString(columnIndex)); }
            if (columnIndex++ < columnCount) { job.setDescription(cursor.getString(columnIndex)); }
            if (columnIndex++ < columnCount) { fullTime = Boolean.valueOf(cursor.getString(columnIndex)); }
            if (columnIndex++ < columnCount) { partTime = Boolean.valueOf(cursor.getString(columnIndex)); }

            job.setFullAndPartTime(fullTime, partTime);
            savedSearches.add(job);
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

    private String sqlEquality(String columnName, String value) {
        return "UPPER(" + columnName + ") = UPPER(\"" + value + "\")";
    }
}
