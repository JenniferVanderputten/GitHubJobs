package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.jpvander.githubjobs.ui.SavedSearchesViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jenva on 2/5/2016.
 */
public class OnGetPositionsResponseCallback implements OnJsonResponseCallback {

    SavedSearchesViewAdapter savedSearchesViewAdapter;

    public OnGetPositionsResponseCallback(SavedSearchesViewAdapter savedSearchesViewAdapter) {
        this.savedSearchesViewAdapter = savedSearchesViewAdapter;
    }

    public void onJsonResponse(int statusCode, JSONObject response) {

        // TODO: This is unexpected and we should add error handling here. For now, don't modify savedSearches.
    }

    public void onJsonResponse(int statusCode, JSONArray response) {

        ArrayList<String> savedSearches = new ArrayList<String>();

        for (int searchIndex = 0; searchIndex < response.length(); searchIndex++) {
            JSONObject savedSearch = null;

            try {

                savedSearch = response.getJSONObject(searchIndex);

                if (null != savedSearch) {
                    savedSearches.add(savedSearch.getString("title"));
                }
            }
            catch (JSONException exception) {
                Log.d("GitHubJobs", "Invalid JSON in response: " + response.toString());
            }
        }

        savedSearchesViewAdapter.updateDataSet(savedSearches);
        //TODO: Handle the pagination.  We'll need a UI change, e.g. "get more results" button.
    }
}
