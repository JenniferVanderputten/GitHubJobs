package com.jpvander.githubjobs.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activities.searches.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.ui.holders.RecyclerTextViewHolder;

import java.util.ArrayList;

public class SavedSearchesViewAdapter extends RecyclerView.Adapter<RecyclerTextViewHolder> {

    private final ViewSavedSearchesFragment.OnFragmentInteractionListener interactionListener;
    private final GitHubJobs jobs;

    public SavedSearchesViewAdapter(
            ViewSavedSearchesFragment.OnFragmentInteractionListener interactionListener,
            GitHubJobs jobs) {
        this.jobs = jobs;
        this.interactionListener = interactionListener;
    }

    @Override
    public RecyclerTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        int textView = R.layout.item_single_text_view;
        View parentView = LayoutInflater.from(context).inflate(textView, parent, false);
        return (new RecyclerTextViewHolder(parentView));
    }

    @Override
    public void onBindViewHolder(RecyclerTextViewHolder holder, final int position) {
        if (null != jobs && position < jobs.size()) {
            ArrayList<String> jobFields = new ArrayList<>();
            jobFields.add(jobs.get(position).getModifiedDescription());
            jobFields.add(jobs.get(position).getModifiedLocation());
            jobs.get(position).setDisplayTitle(jobFields);
            holder.textView.setText(jobs.get(position).getDisplayTitle());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != interactionListener) {
                        interactionListener.onViewSavedSearchesInteraction(jobs.get(position), false);
                    }
                    else {
                        Log.e("GitHubJobs", "interactionListener and/or job is NULL");
                    }
                }
            });
        }
        else {
            holder.textView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (null == jobs) { return 0; }
        return jobs.size();
    }
}
