package com.jpvander.githubjobs.ui.job_details;

import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.view_holders.RecyclerImageAndTextViewHolder;


public class JobDetailsViewAdapter extends JobViewAdapter {

    public JobDetailsViewAdapter(GitHubJobs jobs) {
        super(jobs);
    }

    @Override
    public void onBindViewHolder(RecyclerImageAndTextViewHolder holder, final int position) {
        holder.textView.setText(getJob(position).getDescription());
    }

}

