package com.jpvander.githubjobs.ui.job_details;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class JobDetailsView {

    public JobDetailsView(Activity activity, RecyclerView view, JobDetailsViewAdapter viewAdapter) {

        view.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(viewAdapter);
    }
}
