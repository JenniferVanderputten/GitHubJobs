package com.jpvander.githubjobs.ui.search_results;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activities.searches.ViewSearchResultsFragment;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.ImageDownloader;
import com.jpvander.githubjobs.ui.view_holders.RecyclerImageAndTextViewHolder;

import java.util.ArrayList;

public class SearchResultsViewAdapter extends RecyclerView.Adapter<RecyclerImageAndTextViewHolder> {

    private final ViewSearchResultsFragment fragment;
    private GitHubJobs jobs;

    public SearchResultsViewAdapter(ViewSearchResultsFragment fragment) {
        this.jobs = new GitHubJobs();
        this.fragment = fragment;
    }

    @Override
    public RecyclerImageAndTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View savedSearchesView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image_and_text_view, parent, false);

        return (new RecyclerImageAndTextViewHolder(savedSearchesView));
    }

    @Override
    public void onBindViewHolder(RecyclerImageAndTextViewHolder holder, final int position) {
        Log.d("GitHubJobs", "Position is " + position + ", job title is " + jobs.get(position).getTitle());
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(jobs.get(position).getCompany());
        jobFields.add(jobs.get(position).getTitle());

        String logoUrl = jobs.get(position).getCompany_logo();
        if (null != logoUrl && !logoUrl.isEmpty()) {
            float displayDensity = fragment.getActivity().getResources().getDisplayMetrics().density;
            new ImageDownloader(holder.imageView, displayDensity).execute(logoUrl);
        }
        else {
            //TODO: Add a placeholder image
        }

        holder.textView.setText(jobs.get(position).getDisplayTitle(jobFields));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onSearchResultsItemPressed(jobs.get(position));
            }
        });
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

