package com.jpvander.githubjobs.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.ui.holders.RecyclerTableHolder;


public class JobDetailsViewAdapter extends RecyclerView.Adapter<RecyclerTableHolder> {

    private GitHubJobs jobsSelected;

    public JobDetailsViewAdapter() {
        jobsSelected = new GitHubJobs();
    }

    @Override
    public RecyclerTableHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        int tableView = R.layout.item_single_table_view;
        View parentView  = LayoutInflater.from(context).inflate(tableView, parent, false);
        return (new RecyclerTableHolder(parentView));
    }

    @Override
    public void onBindViewHolder(RecyclerTableHolder holder, int position) {
        //TODO: Modify layout to show logo and info and allow user to view multiple jobs at once
        GitHubJob job = jobsSelected.get(position);
        holder.addTableRowWithHtmlContent("Location:", job.getCompany());
        holder.addTableRowWithHtmlContent("Website:", job.getCompany_url());
        holder.addTableRowWithHtmlContent("Title:", job.getTitle());
        holder.addTableRowWithHtmlContent("Description:", job.getDescription());
    }

    @Override
    public int getItemCount() {
        return jobsSelected.size();
    }
    public void setJobsSelected(GitHubJobs jobsSelected) {
        this.jobsSelected = jobsSelected;
        notifyDataSetChanged();
    }
}

