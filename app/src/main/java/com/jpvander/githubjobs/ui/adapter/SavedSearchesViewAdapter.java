package com.jpvander.githubjobs.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.helper.SavedSearchesDbHelper;
import com.jpvander.githubjobs.fragment.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.ui.holder.RecyclerTextViewAndImageButtonHolder;

public class SavedSearchesViewAdapter extends RecyclerView.Adapter<RecyclerTextViewAndImageButtonHolder> {

    private static final String LOG_LABEL = "GitHubJobs";
    private static final String LISTENER_IS_NULL = " listener is NULL";
    private static final String DELETE_SEARCH_PREPEND = "Delete the following saved search?\n\n";
    private static final String DELETE_YES = "Yes, delete";
    private static final String DELETE_NO = "No, keep";

    private final ViewSavedSearchesFragment.OnFragmentInteractionListener interactionListener;
    private GitHubJobs jobs;
    private final AlertDialog.Builder dialogBuilder;

    public SavedSearchesViewAdapter(
            ViewSavedSearchesFragment.OnFragmentInteractionListener interactionListener,
            GitHubJobs jobs,
            Context context) {
        this.jobs = jobs;
        this.interactionListener = interactionListener;
        dialogBuilder = new AlertDialog.Builder(context);
    }

    @Override
    public RecyclerTextViewAndImageButtonHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        int textView = R.layout.item_single_text_view;
        View parentView = LayoutInflater.from(context).inflate(textView, parent, false);
        return (new RecyclerTextViewAndImageButtonHolder(parentView));
    }

    @Override
    public void onBindViewHolder(RecyclerTextViewAndImageButtonHolder holder, final int position) {
        if (null != jobs && position < jobs.size()) {
            holder.textView.setText(jobs.get(position).getSearchTitle());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != interactionListener) {
                        interactionListener.onViewSavedSearchesInteraction(jobs.get(position), false);
                    } else {
                        Log.e(LOG_LABEL, this.getClass().getSimpleName() + LISTENER_IS_NULL);
                    }
                }
            });

            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final GitHubJob job = jobs.get(position);
                    dialogBuilder.setMessage(DELETE_SEARCH_PREPEND + job.getSearchTitle());
                    final SavedSearchesDbHelper savedSearchesDbHelper = new SavedSearchesDbHelper(view.getContext());

                    dialogBuilder.setPositiveButton(DELETE_YES, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            savedSearchesDbHelper.deleteRow(job.getSavedSearchId());
                            savedSearchesDbHelper.close();
                            jobs.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    dialogBuilder.setNegativeButton(DELETE_NO, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User cancelled, do nothing.
                            savedSearchesDbHelper.close();
                        }
                    });

                    dialogBuilder.create().show();
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
