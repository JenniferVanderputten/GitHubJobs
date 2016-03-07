package com.jpvander.githubjobs.rest.request;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncRestClient {

    private static final String BASE_URL = "https://jobs.github.com/positions";
    private static final String JSON_RESPONSE_APPEND = ".json";
    private static final int TIMEOUT_IN_MILLISECONDS = 7 * 1000;
    private static final AsyncHttpClient client = new AsyncHttpClient();

    //TODO: Detect when network is known by the system to be unavailable so we don't have to time out

    public static void getPositions(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        get(params, responseHandler);
    }

    private static void get(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(TIMEOUT_IN_MILLISECONDS);
        client.get(BASE_URL + JSON_RESPONSE_APPEND, params, responseHandler);
    }
}
