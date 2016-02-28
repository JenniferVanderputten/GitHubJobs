package com.jpvander.githubjobs.rest.response;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class JsonResponseHandler extends JsonHttpResponseHandler {

    private final OnJsonResponseCallback callback;

    public JsonResponseHandler(OnJsonResponseCallback callback) {

        this.callback = callback;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        callback.onJsonSuccessResponse(response);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        callback.onJsonSuccessResponse(response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
        callback.onJsonFailureResponse();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray response) {
        callback.onJsonFailureResponse();
    }
}
