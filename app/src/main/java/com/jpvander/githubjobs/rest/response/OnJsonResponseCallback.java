package com.jpvander.githubjobs.rest.response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jenva on 2/5/2016.
 */
public interface OnJsonResponseCallback {
    public void onJsonResponse(int statusCode, JSONObject response);
    public void onJsonResponse(int statusCode, JSONArray response);
}
