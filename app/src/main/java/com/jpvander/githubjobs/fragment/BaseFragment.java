package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

import com.jpvander.githubjobs.dataset.data.GitHubJobs;

//TODO: Convert to an interface
public class BaseFragment extends Fragment {

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public BaseFragment() {
        // Required empty public constructor
    }

    public boolean shouldShowMenuSearch() { return true; }
    public boolean shouldShowMenuAdd() { return false; }
    public boolean shouldShowMenuLocation() { return false; }
    public void setAcceptsSearchIntent(boolean searchIntentApplied) { }
    public boolean isAcceptingSearchIntent() { return false; }
    public void setLocality(String locality) { }
    public GitHubJobs handleSearchQuery(String query) { return new GitHubJobs(); }
    public BaseFragment clone(GitHubJobs allJobs, String query) {
        return new BaseFragment();
    }
}
