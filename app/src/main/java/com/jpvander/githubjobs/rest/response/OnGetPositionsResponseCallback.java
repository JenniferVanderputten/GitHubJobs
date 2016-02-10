package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.ui.GitHubJobsViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnGetPositionsResponseCallback implements OnJsonResponseCallback {

    private GitHubJobsViewAdapter viewAdapter;

    public OnGetPositionsResponseCallback(GitHubJobsViewAdapter viewAdapter) {
        this.viewAdapter = viewAdapter;
    }

    public void onJsonResponse(int statusCode, JSONObject response) {

        // TODO: Are there any cases where this could actually happen? If so, modify accordingly.

        GitHubJobs jobs = new GitHubJobs();

        try {

            if (null != response) {
                jobs.add(new GitHubJob(
                        response.getString("description"),
                        response.getString("location")));
            }
        }
        catch (JSONException exception) {
            Log.d("GitHubJobs", "Invalid JSON in response: " + response.toString());
        }
    }

    public void onJsonResponse(int statusCode, JSONArray response) {

        GitHubJobs jobs = new GitHubJobs();

        for (int searchIndex = 0; searchIndex < response.length(); searchIndex++) {
            JSONObject savedSearch;

            try {

                savedSearch = response.getJSONObject(searchIndex);

                if (null != savedSearch) {
                    jobs.add(new GitHubJob(
                            savedSearch.getString("description"),
                            savedSearch.getString("location")));
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
