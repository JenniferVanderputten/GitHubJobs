package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activity.ViewSearchesActivity;
import com.jpvander.githubjobs.dataset.helper.SavedSearchesDbHelper;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapter.SavedSearchesViewAdapter;

public class ViewSavedSearchesFragment extends BaseFragment {

    private OnFragmentInteractionListener interactionListener;
    private GitHubJobs jobSearches;
    private SavedSearchesViewAdapter viewAdapter;
    private String title;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public ViewSavedSearchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_saved_searches, container, false);
        SavedSearchesDbHelper dbHelper = new SavedSearchesDbHelper(container.getContext());
        Resources resources = getResources();

        if (0 >= dbHelper.getRowCount()) {
            dbHelper.insertRow(new GitHubJob(
                    resources.getString(R.string.default_description),
                    resources.getString(R.string.default_location),
                    true,
                    true));
        }

        setJobSearches(dbHelper.getSavedSearches());
        dbHelper.close();

        if (null == viewAdapter) {
            viewAdapter = new SavedSearchesViewAdapter(interactionListener, jobSearches, view.getContext());
        }
        else {
            viewAdapter.setJobs(jobSearches);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.saved_searches_recycler);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.addItemDecoration(divider);
        setContextualTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + R.string.must_implement_OFIL);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onViewSavedSearchesInteraction(GitHubJob job, boolean newSearch);
    }

    private void setJobSearches(GitHubJobs jobSearches) {
        if (null != jobSearches) {
            this.jobSearches = jobSearches;
        }
    }

    private String getTitle() {
        return title;
    }

    private void setContextualTitle() {
        title = getResources().getString(R.string.saved_searches_title);
        ViewSearchesActivity activity = (ViewSearchesActivity) getActivity();
        if (null != activity) { activity.setActionBarTitle(getTitle()); }
    }

    @Override
    public boolean shouldShowMenuAdd() {
        return true;
    }
}
