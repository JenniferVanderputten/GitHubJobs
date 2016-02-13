package com.jpvander.githubjobs.ui.job_details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.view_holders.RecyclerImageAndTextViewHolder;

import java.util.ArrayList;

class JobViewAdapter extends RecyclerView.Adapter<RecyclerImageAndTextViewHolder> {

    private GitHubJobs jobs;

    JobViewAdapter(GitHubJobs jobs) {
        this.jobs = jobs;
    }

    @Override
    public RecyclerImageAndTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View jobDetailsView  =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.image_and_text_view_item, parent, false);

        return (new RecyclerImageAndTextViewHolder(jobDetailsView));
    }

    @Override
    public void onBindViewHolder(RecyclerImageAndTextViewHolder holder, int position) {
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
