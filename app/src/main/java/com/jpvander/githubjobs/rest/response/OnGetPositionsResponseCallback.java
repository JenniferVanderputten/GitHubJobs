package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.google.gson.Gson;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.ui.SearchViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpStatus;

public class OnGetPositionsResponseCallback implements OnJsonResponseCallback {

    private final SearchViewAdapter viewAdapter;

    public OnGetPositionsResponseCallback(SearchViewAdapter viewAdapter) {
        this.viewAdapter = viewAdapter;
    }

    public void onJsonResponse(int statusCode, JSONObject response) {

        // TODO: Indicate in UI that no jobs were found? This is not expected.

        Log.d("GitHubJobs", "Received JSONObject, expecting JSONArray: " + response.toString());

        if (HttpStatus.SC_OK != statusCode) {
            Log.d("GitHubJobs", "Response status was OK (200)");
        }
        else {
            Log.d("GitHubJobs", "Response status was " + statusCode);
        }
    }

    public void onJsonResponse(int statusCode, JSONArray response) {

        if (HttpStatus.SC_OK != statusCode) {
            //TODO: Use cached responses?  Inform the user?
            return;
        }

        GitHubJobs jobs = new GitHubJobs();

        for (int searchIndex = 0; searchIndex < response.length(); searchIndex++) {
            JSONObject savedSearch;
            Gson gson = new Gson();

            try {

                savedSearch = response.getJSONObject(searchIndex);

                if (null == savedSearch || 0 >= savedSearch.length()) {
                    //TODO: Indicate in UI that no jobs were found
                    Log.d("GitHubJobs", "No jobs found");
                }
                else {
                    GitHubJob job = gson.fromJson(savedSearch.toString(), GitHubJob.class);

                    if (null != job) {
                        jobs.add(job);
                    }
                    else {
                        jobs.add(new GitHubJob(
                                savedSearch.getString("description"),
                                savedSearch.getString("location")));
                    }
                }
            }
            catch (JSONException exception) {
                Log.d("GitHubJobs", "Invalid JSON in response: " + response.toString());
            }
        }

        viewAdapter.updateDataSet(jobs);
        //TODO: Handle the pagination.  We'll need a UI change, e.g. "get more results" button.
    }
}
