package com.jpvander.githubjobs.ui.job_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.view_holders.RecyclerTableHolder;


public class JobDetailsViewAdapter extends RecyclerView.Adapter<RecyclerTableHolder> {

    private Context context;
    private final GitHubJobs jobs;

    public JobDetailsViewAdapter(GitHubJobs jobs) {
        this.jobs = jobs;
    }

    @Override
    public RecyclerTableHolder onCreateViewHolder(ViewGroup parent, int type) {
        this.context = parent.getContext();
        View jobDetailsView  =
                LayoutInflater.from(context).inflate(R.layout.item_single_table_view, parent, false);

        return (new RecyclerTableHolder(jobDetailsView));
    }

    @Override
    public void onBindViewHolder(RecyclerTableHolder holder, int position) {
        GitHubJob job = jobs.get(position);
        holder.addTableRow(context, "Company:", job.getCompany());
        holder.addTableRow(context, "Title:", job.getTitle());
        holder.addTableRow(context, "Description:", job.getDescription());
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }
}

