package com.jpvander.githubjobs.rest.request;

import android.app.DownloadManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jenva on 2/5/2016.
 */
public class AsyncRestClient {

    private static final String BASE_URL = "https://jobs.github.com/positions";
    private static final String JSON_RESPONSE_APPEND = ".json";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getPositions(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(JSON_RESPONSE_APPEND, params, responseHandler);
    }

    public static void getPositionDetails(String id, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get("/" + id + JSON_RESPONSE_APPEND, params, responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
