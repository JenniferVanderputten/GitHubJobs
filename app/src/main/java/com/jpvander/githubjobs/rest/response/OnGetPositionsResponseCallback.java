package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.google.gson.Gson;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.datasets.data.GitHubJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnGetPositionsResponseCallback implements OnJsonResponseCallback {

    private final SearchResultsCallback callback;

    public OnGetPositionsResponseCallback(SearchResultsCallback callback) {
        this.callback = callback;
    }

    public void onJsonSuccessResponse(JSONObject response) {
        if (null == response) {
            Log.e("GitHubJobs", "OnGetPositionsResponseCallback::onJsonSuccessResponse was null");
        }

        callback.updateSearchResults(null);
    }

    public void onJsonSuccessResponse(JSONArray response) {
        GitHubJobs jobs = new GitHubJobs();

        for (int searchIndex = 0; searchIndex < response.length(); searchIndex++) {
            JSONObject savedSearch;
            Gson gson = new Gson();

            try {

                savedSearch = response.getJSONObject(searchIndex);

                if (null != savedSearch && 0 < savedSearch.length()) {
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
                Log.e("GitHubJobs", "Invalid JSON in response");
            }
        }

        callback.updateSearchResults(jobs);

        //TODO: Add pagination?  We'll need a UI change, e.g. a "get more results" button.
    }

    public void onJsonFailureResponse() {
        callback.updateSearchResults(null);
    }
}
