package com.jpvander.githubjobs.activities.searches;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;

import java.util.ArrayList;

public class ViewSearchesActivity extends AppCompatActivity implements
        ViewSavedSearchesFragment.OnFragmentInteractionListener,
        ViewSearchResultsFragment.OnFragmentInteractionListener {

    private ViewSearchResultsFragment searchResultsFragment;
    private ViewJobDetailsFragment jobDetailsFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);

        Log.d("GitHubJobs", "In ViewSearchesActivity::OnCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = this.getSupportActionBar();
        setActionBarBackButtonEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ViewSavedSearchesFragment savedSearchesFragment = new ViewSavedSearchesFragment();
            searchResultsFragment = new ViewSearchResultsFragment();
            jobDetailsFragment = new ViewJobDetailsFragment();

            savedSearchesFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, savedSearchesFragment).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 1;
        setActionBarBackButtonEnabled(canGoBack);
        return true;
    }

    public void onViewSavedSearchesInteraction(GitHubJob job) {
        // TODO: Add progress indicator for search results screen
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(job.getDescription());
        jobFields.add(job.getLocation());
        Log.d("GitHubJobs", "Job search clicked = " + job.getDisplayTitle(jobFields));

        if (null == searchResultsFragment) { searchResultsFragment = new ViewSearchResultsFragment(); }

        searchResultsFragment.setJobRequested(job);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, searchResultsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        setActionBarBackButtonEnabled(true);
    }

    public void onViewSearchResultsInteraction(GitHubJob job) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(job.getTitle());
        Log.d("GitHubJobs", "Job clicked = " + job.getDisplayTitle(jobFields));

        if (null == jobDetailsFragment) { jobDetailsFragment = new ViewJobDetailsFragment(); }

        jobDetailsFragment.setJobSelected(job);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, jobDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        setActionBarBackButtonEnabled(true);
    }

    private void setActionBarBackButtonEnabled(boolean enabled) {
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }
}
