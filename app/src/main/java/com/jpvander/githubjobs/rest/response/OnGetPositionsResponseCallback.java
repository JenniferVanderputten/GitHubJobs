package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.google.gson.Gson;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.dataset.data.GitHubJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnGetPositionsResponseCallback implements OnJsonResponseCallback {

    private static final String LOG_LABEL = "GitHubJobs";
    private static final String UNEXPECTED_RESPONSE = "Response was not expected: ";

    private final SearchResultsCallback callback;

    public OnGetPositionsResponseCallback(SearchResultsCallback callback) {
        this.callback = callback;
    }

    // Fail if there is anything other than the expected JSONArray response

    public void onJsonSuccessResponse(JSONObject response) {
        Log.e(LOG_LABEL, UNEXPECTED_RESPONSE + response.toString());
        onJsonFailureResponse();
    }

    public void onJsonSuccessResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            onJsonSuccessResponse(jsonArray);
        }
        catch (JSONException exception) {
            Log.e(LOG_LABEL, exception.getMessage(), exception);
            onJsonFailureResponse();
        }
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
                    jobs.add(job);
                }
            }
            catch (JSONException exception) {
                Log.e(LOG_LABEL, exception.getMessage(), exception);
                onJsonFailureResponse();
            }
        }

        callback.updateSearchResults(jobs);

        //TODO: Add pagination?  We'll need a UI change, e.g. a "get more results" button.
    }

    public void onJsonFailureResponse() {
        callback.updateSearchResults(null);
    }
}
