package com.jpvander.githubjobs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.fragment.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.ui.holder.RecyclerTextViewHolder;

public class SavedSearchesViewAdapter extends RecyclerView.Adapter<RecyclerTextViewHolder> {

    private static final String LOG_LABEL = "GitHubJobs";
    private static final String LISTENER_IS_NULL = " listener is NULL";

    private final ViewSavedSearchesFragment.OnFragmentInteractionListener interactionListener;
    private GitHubJobs jobs;

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
            holder.textView.setText(jobs.get(position).getSearchTitle());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != interactionListener) {
                        interactionListener.onViewSavedSearchesInteraction(jobs.get(position), false);
                    }
                    else {
                        Log.e(LOG_LABEL, this.getClass().getSimpleName() + LISTENER_IS_NULL);
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

    public void setJobs(GitHubJobs jobs) {
        this.jobs = jobs;
        notifyDataSetChanged();
    }
}
