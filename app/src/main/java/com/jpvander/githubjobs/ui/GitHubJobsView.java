package com.jpvander.githubjobs.ui;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.datasets.GitHubJobs;

public class GitHubJobsView {

    private static final int ORIENTATION = LinearLayoutManager.VERTICAL;
    private static final float PADDING_DP = 20.0f;

    private RecyclerView view;
    private GitHubJobsViewAdapter viewAdapter;

    public GitHubJobsView(Activity activity, RecyclerView view, GitHubJobs jobs) {
        float displayScale = activity.getResources().getDisplayMetrics().density;
        int paddingPx = (int) (PADDING_DP * displayScale + 0.5f);

        this.view = view;
        this.view.setLayoutManager(
                new LinearLayoutManager(activity, ORIENTATION, false));
        this.viewAdapter = new GitHubJobsViewAdapter(jobs);
        this.view.setAdapter(viewAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        divider.setTopPadding(paddingPx);
        divider.setBottomPadding(paddingPx);
        this.view.addItemDecoration(divider);
    }

    public RecyclerView getView() {
        return this.view;
    }

    public GitHubJobsViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }
}
