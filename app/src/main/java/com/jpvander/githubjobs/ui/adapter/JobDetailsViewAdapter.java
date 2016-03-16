package com.jpvander.githubjobs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.ui.holder.RecyclerTableHolder;

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
        holder.addRowWithHtmlContent(GitHubJob.COMPANY_LABEL + ":", job.getCompany());
        holder.addRowWithHtmlContent(GitHubJob.LOCATION_LABEL + ":", job.getLocation());
        holder.addRowWithHtmlContent(GitHubJob.TITLE_LABEL + ":", job.getTitle());
        holder.addRowWithHtmlContent(GitHubJob.WEBSITE_LABEL + ":", job.getCompany_url());
        holder.addRowWithHtmlContent(GitHubJob.TYPE_LABEL + ":", job.getType());
        holder.addRowWithHtmlContent(GitHubJob.DESCRIPTION_LABEL + ":", job.getDescription());
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

