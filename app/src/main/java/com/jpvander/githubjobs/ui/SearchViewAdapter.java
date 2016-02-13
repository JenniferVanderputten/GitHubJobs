package com.jpvander.githubjobs.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.datasets.GitHubJobs;

import java.util.ArrayList;

public class SearchViewAdapter extends RecyclerView.Adapter<RecyclerSingleLineTextViewHolder> {

    private GitHubJobs jobs;

    SearchViewAdapter(GitHubJobs jobs) {
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
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(getJob(position).getDescription());
        jobFields.add(getJob(position).getLocation());
        holder.textView.setText(jobs.get(position).getDisplayTitle(jobFields));
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void updateDataSet(GitHubJobs jobs) {
        this.jobs = jobs;
        this.notifyDataSetChanged();
    }

    GitHubJob getJob(int position) {
        return jobs.get(position);
    }

}