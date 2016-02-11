package com.jpvander.githubjobs.activities.searches.saved;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;

import java.util.ArrayList;

public class ViewSearchesActivity extends AppCompatActivity
        implements ViewSavedSearchesFragment.OnFragmentInteractionListener,
        ViewSearchResultsFragment.OnFragmentInteractionListener {

    private ViewSavedSearchesFragment savedSearchesFragment;
    private ViewSearchResultsFragment searchResultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            savedSearchesFragment = new ViewSavedSearchesFragment();
            searchResultsFragment = new ViewSearchResultsFragment();

            savedSearchesFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, savedSearchesFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: Do we need settings for this app?
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount() > 1;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
        return true;
    }

    public void onViewSavedSearchesInteraction(GitHubJob job) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(job.getDescription());
        jobFields.add(job.getLocation());
        Log.d("GitHubJobs", "Job search clicked = " + job.getDisplayTitle(jobFields));
        searchResultsFragment.setJobRequested(job);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, searchResultsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onViewSearchResultsInteraction(GitHubJob job) {
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(job.getTitle());
        Log.d("GitHubJobs", "Job clicked = " + job.getDisplayTitle(jobFields));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
