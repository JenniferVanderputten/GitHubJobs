package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private final static String QUERY_STATE_LABEL = "searchQuery";
    private final static String SEARCH_INTENT_APPLIED_STATE_LABEL = "acceptsSearchIntent";

    private OnFragmentInteractionListener interactionListener;
    private GitHubJobs jobSearches;
    private SavedSearchesViewAdapter viewAdapter;
    private String searchQuery;
    private String title;
    private boolean acceptsSearchIntent = true;
    private boolean should_show_add = true;
    private boolean should_show_search = true;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public ViewSavedSearchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedState) {
        retrieveState(savedState);
        saveState(savedState);
        super.onCreate(savedState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Add delete button to each item
        retrieveState(savedState);
        printStatePropertiesIfDebug();
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_saved_searches, container, false);
        SavedSearchesDbHelper dbHelper = new SavedSearchesDbHelper(container.getContext());
        Resources resources = getResources();

        if (0 >= dbHelper.getRowCount()) {
            dbHelper.insertRow(new GitHubJob(
                    resources.getString(R.string.default_description),
                    resources.getString(R.string.default_location)));
        }

        setJobSearches(dbHelper.getSavedSearches());
        dbHelper.close();
        if (isFilteredBySearch()) { setJobSearches(handleSearchQuery(searchQuery)); }

        if (null == viewAdapter) {
            viewAdapter = new SavedSearchesViewAdapter(interactionListener, jobSearches);
        }
        else {
            viewAdapter.setJobs(jobSearches);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fvss_recycler);
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

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        saveState(savedState);
        super.onSaveInstanceState(savedState);
    }

    private boolean isFilteredBySearch() {
        return (null != searchQuery && !searchQuery.isEmpty());
    }

    private void printStatePropertiesIfDebug() {
        if (0 != (getActivity().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
            Log.d("GitHubJobs", System.identityHashCode(this) + " VSS searchQuery: " + searchQuery);
            Log.d("GitHubJobs", System.identityHashCode(this) + " VSS accepting intent? " + isAcceptingSearchIntent());

        }
    }

    private void retrieveState(Bundle savedState) {
        if (null != savedState) {
            setSearchQuery(savedState.getString(QUERY_STATE_LABEL));
        }
    }

    private void saveState(Bundle savedState) {
        if (null != savedState) {
            savedState.putString(QUERY_STATE_LABEL, searchQuery);
            savedState.putBoolean(SEARCH_INTENT_APPLIED_STATE_LABEL, acceptsSearchIntent);
            setAcceptsSearchIntent(savedState.getBoolean(SEARCH_INTENT_APPLIED_STATE_LABEL));
        }
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
        if (isFilteredBySearch()) {
            title = getResources().getString(R.string.saved_searches_title, ": ") + searchQuery;
        }
        else {
            title = getResources().getString(R.string.saved_searches_title, "");
        }

        ViewSearchesActivity activity = (ViewSearchesActivity) getActivity();
        if (null != activity) { activity.setActionBarTitle(getTitle()); }
    }

    @Override
    public boolean shouldShowMenuAdd() {
        return should_show_add;
    }

    @Override
    public boolean shouldShowMenuSearch() { return should_show_search; }

    private void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;

        if (isFilteredBySearch()) {
            acceptsSearchIntent = false;
            should_show_add = false;
            should_show_search = false;
        }
    }

    @Override
    public GitHubJobs handleSearchQuery(String query) {
        GitHubJobs matchingJobs = new GitHubJobs();

        if (null != jobSearches) {
            for (int jobIndex = 0; jobIndex < jobSearches.size(); jobIndex++) {
                GitHubJob job = jobSearches.get(jobIndex);

                if (job.getDisplayTitle().toLowerCase().contains(query.toLowerCase())) {
                    matchingJobs.add(job);
                }
            }
        }

        return matchingJobs;
    }

    @Override
    public ViewSavedSearchesFragment clone(GitHubJobs allJobs, String query) {
        ViewSavedSearchesFragment newFragment = new ViewSavedSearchesFragment();
        newFragment.setJobSearches(allJobs);
        newFragment.setSearchQuery(query);
        return newFragment;
    }

    @Override
    public void setAcceptsSearchIntent(boolean acceptsIntent) {
        this.acceptsSearchIntent = acceptsIntent;
    }

    @Override
    public boolean isAcceptingSearchIntent() {
        return acceptsSearchIntent;
    }
}
