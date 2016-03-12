package com.jpvander.githubjobs.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jpvander.githubjobs.fragment.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.data.GitHubJobs;
import com.jpvander.githubjobs.fragment.NewSearchFragment;
import com.jpvander.githubjobs.fragment.ViewJobDetailsFragment;
import com.jpvander.githubjobs.fragment.ViewSavedSearchesFragment;
import com.jpvander.githubjobs.fragment.ViewSearchResultsFragment;
import com.jpvander.githubjobs.helper.LocationServiceHelper;

@SuppressLint("unused")
public class ViewSearchesActivity extends AppCompatActivity implements
        ViewSavedSearchesFragment.OnFragmentInteractionListener,
        ViewSearchResultsFragment.OnFragmentInteractionListener,
        NewSearchFragment.OnFragmentInteractionListener {

    private ViewSavedSearchesFragment savedSearchesFragment;
    private ViewSearchResultsFragment searchResultsFragment;
    private ViewJobDetailsFragment jobDetailsFragment;
    private NewSearchFragment newSearchFragment;
    private LocationServiceHelper locationHelper;
    private ProgressDialog spinner;
    private Menu menu;

    // TODO: Handle pre-23 backup of the databases

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_view_searches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.avs_toolbar);
        setSupportActionBar(toolbar);
        locationHelper = new LocationServiceHelper(this);

        spinner = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getResources().getString(R.string.spinner_getting_location));
        spinner.setCancelable(false);

        if (null == savedSearchesFragment) {
            savedSearchesFragment = new ViewSavedSearchesFragment();
        }

        if (null == savedState) {
            transactFragment(savedSearchesFragment, false, false);
        }
        else {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.avs_fragment_container);
            setActionBar(fragment, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] results) {

        BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.avs_fragment_container);

        if (locationHelper.permissionGranted(requestCode, results)) {
            Location location = locationHelper.getLocation(this);
            fragment.setLocality(locationHelper.getLocality(location));
        }
        else {
            //TODO: "Grey out" the menu_location item in some way? Make it invisible?
            menu.findItem(R.id.mab_menu_location).setEnabled(false);
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
                .findFragmentById(R.id.avs_fragment_container);
        setActionBar(fragment, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        menu.findItem(R.id.mab_menu_location).setEnabled(false);

        if (locationHelper.hasPermission() || !locationHelper.permissionWasRequested()) {
            menu.findItem(R.id.mab_menu_location).setEnabled(true);
        }

        setMenuItemVisibilities(null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mab_menu_add:
                onViewSavedSearchesInteraction(null, true);
                return true;

            case R.id.mab_menu_location:
                if (null != spinner) { spinner.show(); }

                BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.avs_fragment_container);

                if (locationHelper.hasPermission()) {
                    Location location = locationHelper.getLocation(this);
                    fragment.setLocality(locationHelper.getLocality(location));
                }
                else {
                    locationHelper.requestPermissions(this);
                }

                spinner.cancel();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != spinner) { spinner.cancel(); }
    }

    public void onViewSavedSearchesInteraction(GitHubJob job, boolean newSearch) {
        if (newSearch) {
            if (null == newSearchFragment) { newSearchFragment = new NewSearchFragment(); }
            transactFragment(newSearchFragment, false, true);
        }
        else {
            if (null == searchResultsFragment) { searchResultsFragment = new ViewSearchResultsFragment(); }
            searchResultsFragment.setJobRequested(job);
            searchResultsFragment.setJobsFound(null);
            transactFragment(searchResultsFragment, false, true);
        }
    }

    public void onViewSearchResultsInteraction(GitHubJob job) {
        GitHubJobs jobs = new GitHubJobs();
        jobs.add(job);
        if (null == jobDetailsFragment) { jobDetailsFragment = new ViewJobDetailsFragment(); }
        jobDetailsFragment.setJobsSelected(jobs);
        transactFragment(jobDetailsFragment, false, true);
    }

    public void onNewSearchInteraction(GitHubJob job) {
        if (null == searchResultsFragment) { searchResultsFragment = new ViewSearchResultsFragment(); }
        searchResultsFragment.setJobRequested(job);
        searchResultsFragment.setJobsFound(null);
        transactFragment(searchResultsFragment, true, true);
    }

    private void setActionBar(BaseFragment fragment, boolean forceBackButtonEnabled) {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        boolean canGoBack = forceBackButtonEnabled;
        if (!forceBackButtonEnabled) { canGoBack = backStackCount > 0; }
        setActionBarBackUpEnabled(canGoBack);
        setMenuItemVisibilities(fragment);
    }

    private void setActionBarBackUpEnabled(boolean canGoBack) {
        ActionBar actionBar = this.getSupportActionBar();

        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(canGoBack);
        }
    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) { actionBar.setTitle(title); }
    }

    private void setMenuItemVisibilities(BaseFragment fragment) {
        if (null == fragment) {
            fragment = (BaseFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.avs_fragment_container);
        }

        if (null != fragment && null != menu) {
            menu.findItem(R.id.mab_menu_add).setVisible(fragment.shouldShowMenuAdd());
            menu.findItem(R.id.mab_menu_location).setVisible(fragment.shouldShowMenuLocation());
        }
    }

    private void transactFragment(BaseFragment fragment, boolean skipPreviousFragment,
                                  boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (skipPreviousFragment) { getSupportFragmentManager().popBackStack(); }
        transaction.replace(R.id.avs_fragment_container, fragment);
        if (addToBackStack) { transaction.addToBackStack(null); }
        transaction.commit();
        setActionBar(fragment, addToBackStack);
    }
}
