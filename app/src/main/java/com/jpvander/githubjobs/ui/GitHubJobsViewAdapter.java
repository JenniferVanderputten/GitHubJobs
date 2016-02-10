package com.jpvander.githubjobs.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJobs;

public class GitHubJobsViewAdapter extends RecyclerView.Adapter<RecyclerSingleLineTextViewHolder> {

    private GitHubJobs jobs;

    public GitHubJobsViewAdapter(GitHubJobs jobs) {
        this.jobs = jobs;
    }

    @Override
    public RecyclerSingleLineTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View savedSearchesView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.search_result_item, parent, false);

        return (new RecyclerSingleLineTextViewHolder(savedSearchesView));
    }

    @Override
    public void onBindViewHolder(RecyclerSingleLineTextViewHolder holder, int position) {
        holder.textView.setText(jobs.get(position).getDisplayTitle());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void updateDataSet(GitHubJobs jobs) {
        this.jobs = jobs;
        this.notifyDataSetChanged();
    }
}
