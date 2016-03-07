package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activity.ViewSearchesActivity;
import com.jpvander.githubjobs.dataset.helper.SearchResultsDbHelper;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.rest.response.SearchResultsCallback;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapter.SearchResultsViewAdapter;

import java.util.ArrayList;


public class ViewSearchResultsFragment extends BaseFragment {

    private static final String QUERY_STATE_LABEL = "searchQuery";
    private static final String JOB_REQUESTED_STATE_LABEL = "jobRequested";
    private final static String SEARCH_INTENT_APPLIED_STATE_LABEL = "acceptsSearchIntent";

    private OnFragmentInteractionListener interactionListener;
    private GitHubJob jobRequested;
    private GitHubJobs jobsFound;
    private ProgressDialog spinner;
    private SearchResultsDbHelper dbHelper;
    private String title;
    private SearchResultsViewAdapter viewAdapter;
    private String searchQuery;
    private boolean acceptsSearchIntent = true;
    private boolean should_show_search = true;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public ViewSearchResultsFragment() {
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
        if (null == dbHelper) { dbHelper = new SearchResultsDbHelper(getContext()); }
        retrieveState(savedState);
        printStatePropertiesIfDebug();
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_search_results, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fvsr_recycler);
        final TextView emptyView = (TextView) view.findViewById(R.id.fvsr_empty_view);

        spinner = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getResources().getString(R.string.spinner_searching_jobs));
        spinner.setCancelable(true);

        viewAdapter = new SearchResultsViewAdapter(interactionListener);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(viewAdapter);
        recyclerView.addItemDecoration(divider);

        OnGetPositionsResponseCallback callback = new OnGetPositionsResponseCallback(
                new SearchResultsCallback() {
                    @Override
                    public void updateSearchResults(GitHubJobs jobs) {
                        if (null != spinner) { spinner.cancel(); }

                        if (null != jobRequested) {
                            if (null != jobs && 0 < jobs.size()) {
                                setJobsFound(setSearchIds(jobRequested.getSavedSearchId(), jobs));
                                dbHelper.saveSearchResults(jobRequested.getSavedSearchId(), jobsFound);
                            }
                            else {
                                setJobsFound(dbHelper.getSearchResults(jobRequested.getSavedSearchId()));
                            }
                        }

                        setContentView(recyclerView, emptyView);
                    }
                }
        );

        JsonResponseHandler responseHandler = new JsonResponseHandler(callback);

        if (null == jobRequested) {
            setContentView(recyclerView, emptyView);
        }
        else {
            if (!areJobsFound()) {
                if (null != spinner) { spinner.show(); }
                AsyncRestClient.getPositions(jobRequested.getRequestParams(), responseHandler);
            }
            else {
                setContentView(recyclerView, emptyView);
            }
        }

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
    public void onStop() {
        super.onStop();
        if (null != spinner) { spinner.cancel(); }
        if (null != dbHelper) { dbHelper.close(); }
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

    @Override
    public boolean shouldShowMenuSearch() {
        return should_show_search;
    }

    private boolean isFilteredBySearch() {
        return (null != searchQuery && !searchQuery.isEmpty());
    }

    private boolean areJobsFound() {
        return (null != jobsFound && 0 < jobsFound.size());
    }

    public void setJobRequested(GitHubJob jobRequested) {
        if (null != jobRequested) {
            this.jobRequested = jobRequested;
            ArrayList<String> jobFields = new ArrayList<>();
            jobFields.add(jobRequested.getModifiedDescription());
            jobFields.add(jobRequested.getModifiedLocation());
            jobRequested.setDisplayTitle(jobFields);
            setContextualTitle();
        }
    }

    public interface OnFragmentInteractionListener {
        void onViewSearchResultsInteraction(GitHubJob job);
    }

    private String getTitle() {
        return title;
    }

    private void setContextualTitle() {
        if (null != jobRequested) {
            this.title = jobRequested.getDisplayTitle();
            if (isFilteredBySearch()) { this.title = this.title + ": "  + searchQuery; }
        }
        else {
            this.title = getResources().getString(R.string.search_results_title);
        }

        ViewSearchesActivity activity = (ViewSearchesActivity) getActivity();
        if (null != activity) { activity.setActionBarTitle(getTitle()); }
    }

    private void setContentView(RecyclerView recyclerView, TextView emptyView) {
        if (areJobsFound()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            if (isFilteredBySearch()) { setJobsFound(handleSearchQuery(searchQuery)); }
            viewAdapter.updateDataSet(jobsFound);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        setContextualTitle();
    }

    private void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;

        if (isFilteredBySearch()) {
            acceptsSearchIntent = false;
            should_show_search = false;
        }
    }

    public void setJobsFound(GitHubJobs jobsFound) {
        this.jobsFound = jobsFound;
    }

    @Override
    public GitHubJobs handleSearchQuery(String query) {
        GitHubJobs matchingJobs = new GitHubJobs();

        if (null != jobsFound) {
            for (int jobIndex = 0; jobIndex < jobsFound.size(); jobIndex++) {
                GitHubJob job = jobsFound.get(jobIndex);

                if (job.getDisplayTitle().toLowerCase().contains(query.toLowerCase())) {
                    matchingJobs.add(job);
                }
            }
        }

        return matchingJobs;
    }

    @Override
    public ViewSearchResultsFragment clone(GitHubJobs allJobs, String query) {
        ViewSearchResultsFragment newFragment = new ViewSearchResultsFragment();
        newFragment.setJobsFound(allJobs);
        newFragment.setSearchQuery(query);
        if (null != jobRequested) { newFragment.setJobRequested(jobRequested); }
        return newFragment;
    }

    private GitHubJobs setSearchIds(long searchId, GitHubJobs jobs) {
        GitHubJobs modifiedJobs = new GitHubJobs();

        for (int jobIndex = 0; jobIndex < jobs.size(); jobIndex++) {
            modifiedJobs.add(jobs.get(jobIndex));
            modifiedJobs.get(jobIndex).setSavedSearchId(searchId);
        }

        return modifiedJobs;
    }

    private void saveState(Bundle savedState) {
        if (null != savedState) {
            String jobRequestedAsJSON = new Gson().toJson(jobRequested);
            savedState.putString(JOB_REQUESTED_STATE_LABEL, jobRequestedAsJSON);
            savedState.putString(QUERY_STATE_LABEL, searchQuery);
            savedState.putBoolean(SEARCH_INTENT_APPLIED_STATE_LABEL, acceptsSearchIntent);
        }
    }

    private void printStatePropertiesIfDebug() {
        if (0 != (getActivity().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
            Log.d("GitHubJobs", System.identityHashCode(this) + " VSR searchQuery: " + searchQuery);
            Log.d("GitHubJobs", System.identityHashCode(this) + " VSR accepting intent? " + isAcceptingSearchIntent());


            if (null == jobRequested) {
                Log.d("GitHubJobs", System.identityHashCode(this) + " VSR jobRequested is NULL");
            }
            else {
                Log.d("GitHubJobs", System.identityHashCode(this) + " VSR savedSearchId: " + jobRequested.getSavedSearchId());
                Log.d("GitHubJobs", System.identityHashCode(this) + " VSR jobRequested: " + jobRequested.getDisplayTitle());
            }
        }
    }

    private void retrieveState(Bundle savedState) {
        printStatePropertiesIfDebug();
        if (null != savedState) {
            String jobRequestedAsJSON = savedState.getString(JOB_REQUESTED_STATE_LABEL);
            setJobRequested(new Gson().fromJson(jobRequestedAsJSON, GitHubJob.class));
            setSearchQuery(savedState.getString(QUERY_STATE_LABEL));
            setAcceptsSearchIntent(savedState.getBoolean(SEARCH_INTENT_APPLIED_STATE_LABEL));

            if (null != jobRequested) {
                if (null == dbHelper) { dbHelper = new SearchResultsDbHelper(getContext()); }
                setJobsFound(dbHelper.getSearchResults(jobRequested.getSavedSearchId()));
            }

            setContextualTitle();
        }
    }

    @Override
    public void setAcceptsSearchIntent(boolean searchIntentApplied) {
        this.acceptsSearchIntent = searchIntentApplied;
    }

    @Override
    public boolean isAcceptingSearchIntent() {
        return acceptsSearchIntent;
    }
}
