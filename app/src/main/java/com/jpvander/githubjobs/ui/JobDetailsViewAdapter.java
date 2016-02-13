package com.jpvander.githubjobs.ui;

import com.jpvander.githubjobs.activities.searches.saved.ViewJobDetailsFragment;
import com.jpvander.githubjobs.datasets.GitHubJobs;

public class JobDetailsViewAdapter extends SearchViewAdapter {

    private final ViewJobDetailsFragment fragment;

    public JobDetailsViewAdapter(ViewJobDetailsFragment fragment, GitHubJobs jobs) {
        super(jobs);
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(RecyclerSingleLineTextViewHolder holder, final int position) {
        holder.textView.setText(getJob(position).getDescription());
    }

}

