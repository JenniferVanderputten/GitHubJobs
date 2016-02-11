package com.jpvander.githubjobs.ui;

import android.view.View;

import com.jpvander.githubjobs.activities.searches.saved.ViewSearchResultsFragment;
import com.jpvander.githubjobs.datasets.GitHubJobs;

import java.util.ArrayList;

/**
 * Created by jenva on 2/11/2016.
 */
public class SearchResultsViewAdapter extends GitHubJobsViewAdapter {

    private ViewSearchResultsFragment fragment;

    public SearchResultsViewAdapter(ViewSearchResultsFragment fragment, GitHubJobs jobs) {
        super(jobs);
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(RecyclerSingleLineTextViewHolder holder, final int position) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(getJob(position).getTitle());
        holder.textView.setText(getJob(position).getDisplayTitle(jobFields));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onSearchResultsItemPressed(getJob(position));
            }
        });
    }

}

