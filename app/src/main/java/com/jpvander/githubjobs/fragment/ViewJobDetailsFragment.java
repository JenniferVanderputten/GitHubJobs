package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activity.ViewSearchesActivity;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.dataset.helper.SearchResultsDbHelper;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapter.JobDetailsViewAdapter;

import java.util.ArrayList;

public class ViewJobDetailsFragment extends BaseFragment {

    private static final boolean SHOULD_SHOW_SEARCH = false;
    private static final String JOB_STATE_LABEL = "jobSelected";

    private JobDetailsViewAdapter viewAdapter;
    private GitHubJobs jobsSelected;
    private SearchResultsDbHelper dbHelper;
    private String title;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public ViewJobDetailsFragment() {
        // Required empty public constructor.
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
        View view = inflater.inflate(R.layout.fragment_view_job_details, container, false);
        if (null == viewAdapter) { viewAdapter = new JobDetailsViewAdapter(); }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fvjd_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.addItemDecoration(divider);
        setContextualTitle();
        return view;
    }

    public void setJobsSelected(GitHubJobs jobsSelected) {
        if (null != jobsSelected) {
            if (null == viewAdapter) { viewAdapter = new JobDetailsViewAdapter(); }
            viewAdapter.setJobsSelected(jobsSelected);
            this.jobsSelected = jobsSelected;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != dbHelper) { dbHelper.close(); }
    }

    private String getTitle() {
        return title;
    }

    private void setContextualTitle() {
        this.title = getResources().getString(R.string.job_details_title);
        ViewSearchesActivity activity = (ViewSearchesActivity) getActivity();
        if (null != activity) { activity.setActionBarTitle(getTitle()); }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        saveState(savedState);
        super.onSaveInstanceState(savedState);
    }

    private void printStatePropertiesIfDebug() {
        if (0 != (getActivity().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
            Log.d("GitHubJobs", System.identityHashCode(this) + " VJD accepting intent? " + isAcceptingSearchIntent());

            if (null == jobsSelected) {
                Log.d("GitHubJobs", System.identityHashCode(this) + " VJD jobRequested is NULL");
            }
            else {
                if (0 < jobsSelected.size()) {
                    Log.d("GitHubJobs", System.identityHashCode(this) + " VJD jobRequested: " + jobsSelected.get(0).getDisplayTitle());
                }
                else {
                    Log.d("GitHubJobs", System.identityHashCode(this) + " VJD jobRequested is empty");
                }
            }
        }
    }

    private void retrieveState(Bundle savedState) {
        if (null != savedState) {
            ArrayList<String> jobIDs = savedState.getStringArrayList(JOB_STATE_LABEL);

            if (null != jobIDs) {
                jobsSelected = new GitHubJobs();
                if (null == dbHelper) { dbHelper = new SearchResultsDbHelper(getContext()); }

                for (int jobIndex = 0; jobIndex < jobIDs.size(); jobIndex++) {
                    jobsSelected.add(dbHelper.getJob(jobIDs.get(jobIndex)));
                }

                setJobsSelected(jobsSelected);
            }
        }
    }

    private void saveState(Bundle savedState) {
        if (null != savedState && null != jobsSelected) {
            ArrayList<String> jobIDs = new ArrayList<>();

            for (int jobIndex = 0; jobIndex < jobsSelected.size(); jobIndex++) {
                jobIDs.add(jobsSelected.get(jobIndex).getId());
            }

            savedState.putStringArrayList(JOB_STATE_LABEL, jobIDs);
        }
    }

    @Override
    public boolean shouldShowMenuSearch() {
        return SHOULD_SHOW_SEARCH;
    }
}
