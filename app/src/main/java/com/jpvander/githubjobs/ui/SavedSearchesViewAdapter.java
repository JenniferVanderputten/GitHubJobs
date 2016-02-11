package com.jpvander.githubjobs.ui;

import android.view.View;

import com.jpvander.githubjobs.activities.searches.saved.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.datasets.GitHubJobs;

import java.util.ArrayList;

/**
 * Created by jenva on 2/11/2016.
 */
public class SavedSearchesViewAdapter extends GitHubJobsViewAdapter {

    private ViewSavedSearchesFragment fragment;

    public SavedSearchesViewAdapter(ViewSavedSearchesFragment fragment, GitHubJobs jobs) {
        super(jobs);
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(RecyclerSingleLineTextViewHolder holder, final int position) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(getJob(position).getDescription());
        jobFields.add(getJob(position).getLocation());
        holder.textView.setText(getJob(position).getDisplayTitle(jobFields));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onSavedSearchesItemPressed(getJob(position));
            }
        });
    }

}
