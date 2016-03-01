package com.jpvander.githubjobs.activities.searches;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.helpers.SavedSearchesDbHelper;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;

public class ViewSearchesActivity extends AppCompatActivity implements
        ViewSavedSearchesFragment.OnFragmentInteractionListener,
        ViewSearchResultsFragment.OnFragmentInteractionListener,
        NewSearchFragment.OnFragmentInteractionListener {

    private ViewSavedSearchesFragment savedSearchesFragment;
    private ViewSearchResultsFragment searchResultsFragment;
    private ViewJobDetailsFragment jobDetailsFragment;
    private NewSearchFragment newSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null == savedSearchesFragment) {
            savedSearchesFragment = new ViewSavedSearchesFragment();
        }

        if (null == savedInstanceState) {
            transactFragment(savedSearchesFragment, savedSearchesFragment.getTitle(), false, false);
        }
        else {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
            setActionBar(fragment.getTitle(), false);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        setActionBar(fragment.getTitle(), false);
    }

    public void onViewSavedSearchesInteraction(GitHubJob job, boolean newSearch) {
        if (newSearch) {
            if (null == newSearchFragment) { newSearchFragment = new NewSearchFragment(); }
            transactFragment(newSearchFragment, newSearchFragment.getTitle(), false, true);
        }
        else {
            if (null == searchResultsFragment) { searchResultsFragment = new ViewSearchResultsFragment(); }
            searchResultsFragment.setJobRequested(job);
            transactFragment(searchResultsFragment, searchResultsFragment.getTitle(), false, true);
        }
    }

    public void onViewSearchResultsInteraction(GitHubJob job) {
        GitHubJobs jobs = new GitHubJobs();
        jobs.add(job);
        if (null == jobDetailsFragment) { jobDetailsFragment = new ViewJobDetailsFragment(); }
        jobDetailsFragment.setJobsSelected(jobs);
        transactFragment(jobDetailsFragment, jobDetailsFragment.getTitle(), false, true);
    }

    public void onNewSearchInteraction(GitHubJob job) {
        (new SavedSearchesDbHelper(this)).insertRow(job);
        if (null == searchResultsFragment) { searchResultsFragment = new ViewSearchResultsFragment(); }
        searchResultsFragment.setJobRequested(job);
        transactFragment(searchResultsFragment, searchResultsFragment.getTitle(), true, true);
    }

    private void setActionBar(String title, boolean forceBackButtonEnabled) {
        ActionBar actionBar = this.getSupportActionBar();
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        boolean canGoBack = forceBackButtonEnabled;
        if (!forceBackButtonEnabled) { canGoBack = backStackCount > 0; }

        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(canGoBack);
            setActionBarTitle(actionBar, title);
        }
    }

    private void setActionBarTitle(ActionBar actionBar, String title) {
        if (null == actionBar) { actionBar = getSupportActionBar(); }
        if (null != actionBar) { actionBar.setTitle(title); }
    }

    private void transactFragment(Fragment fragment, String actionBarTitle,
                                  boolean skipPreviousFragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (skipPreviousFragment) { getSupportFragmentManager().popBackStack(); }
        transaction.replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
        setActionBar(actionBarTitle, addToBackStack);
    }
}
