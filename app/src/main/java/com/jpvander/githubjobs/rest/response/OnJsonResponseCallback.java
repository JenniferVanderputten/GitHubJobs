package com.jpvander.githubjobs.rest.response;

import org.json.JSONArray;
import org.json.JSONObject;

interface OnJsonResponseCallback {
    void onJsonSuccessResponse(JSONObject response);
    void onJsonSuccessResponse(JSONArray response);
    void onJsonFailureResponse();
}
