package com.jpvander.githubjobs.ui;

import android.view.View;

import com.jpvander.githubjobs.activities.searches.saved.ViewSearchResultsFragment;
import com.jpvander.githubjobs.datasets.GitHubJobs;

import java.util.ArrayList;

public class SearchResultsViewAdapter extends SearchViewAdapter {

    private final ViewSearchResultsFragment fragment;

    public SearchResultsViewAdapter(ViewSearchResultsFragment fragment) {
        super(new GitHubJobs());
        this.fragment = fragment;
    }

    @Override
    public void onBindViewHolder(RecyclerSingleLineTextViewHolder holder, final int position) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(getJob(position).getCompany());
        jobFields.add(getJob(position).getTitle());
        // TODO: Title, company name, and logo -- will need layout change
        holder.textView.setText(getJob(position).getDisplayTitle(jobFields));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onSearchResultsItemPressed(getJob(position));
            }
        });
    }

}

