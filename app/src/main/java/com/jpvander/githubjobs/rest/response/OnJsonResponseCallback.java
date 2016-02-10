package com.jpvander.githubjobs.rest.response;

import org.json.JSONArray;
import org.json.JSONObject;

interface OnJsonResponseCallback {
    void onJsonResponse(int statusCode, JSONObject response);
    void onJsonResponse(int statusCode, JSONArray response);
}
