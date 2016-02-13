package com.jpvander.githubjobs.ui.saved_searches;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activities.searches.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.view_holders.RecyclerTextViewHolder;

import java.util.ArrayList;

public class SavedSearchesViewAdapter extends RecyclerView.Adapter<RecyclerTextViewHolder> {

    private final ViewSavedSearchesFragment fragment;
    private GitHubJobs jobs;

    public SavedSearchesViewAdapter(ViewSavedSearchesFragment fragment, GitHubJobs jobs) {
        this.jobs = jobs;
        this.fragment = fragment;
    }

    @Override
    public RecyclerTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View savedSearchesView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_text_view_item, parent, false);

        return (new RecyclerTextViewHolder(savedSearchesView));
    }

    @Override
    public void onBindViewHolder(RecyclerTextViewHolder holder, final int position) {
        ArrayList<String> jobFields = new ArrayList<>();
        final GitHubJob job = jobs.get(position);
        jobFields.add(job.getDescription());
        jobFields.add(job.getLocation());
        holder.textView.setText(job.getDisplayTitle(jobFields));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onSavedSearchesItemPressed(job);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }
}
