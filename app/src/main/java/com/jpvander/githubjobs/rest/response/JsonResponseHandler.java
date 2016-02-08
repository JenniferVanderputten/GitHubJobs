package com.jpvander.githubjobs.rest.response;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/**
 * Created by jenva on 2/5/2016.
 */
public class JsonResponseHandler extends JsonHttpResponseHandler {

    private OnJsonResponseCallback callback;

    public JsonResponseHandler(OnJsonResponseCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

        try {

            Log.d("GitHubJobs", "Response was JSON Object: " + response.toString());
            callback.onJsonResponse(statusCode, response);
        }
        catch (Exception exception) {

            Log.d("GitHubJobs", "Response was JSON Object; exception while logging the object: "
                    + exception.getMessage());
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

        try {

            Log.d("GitHubJobs", "Response was JSON Array: " + response.toString());
            callback.onJsonResponse(statusCode, response);
        }
        catch (Exception exception) {

            Log.d("GitHubJobs", "Response was JSON Array; exception while logging the array: "
                    + exception.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray response) {

        try {

            Log.d("GitHubJobs", "Response was error: " + response.toString());
        }
        catch (Exception exception) {

            Log.d("GitHubJobs", "Response was error; exception while logging the error: "
                    + exception.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {

        try {

            Log.d("GitHubJobs", "Response was error: " + response.toString());
        }
        catch (Exception exception) {

            Log.d("GitHubJobs", "Response was error; exception while logging the error: "
                    + exception.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {

        try {

            Log.d("GitHubJobs", "Response was error: " + response.toString());
        }
        catch (Exception exception) {

            Log.d("GitHubJobs", "Response was error; exception while logging the error: "
                    + exception.getMessage());
        }
    }
}
