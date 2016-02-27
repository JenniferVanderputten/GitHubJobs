package com.jpvander.githubjobs.ui.adapters;

import com.jpvander.githubjobs.datasets.data.GitHubJobs;

public interface SearchResultsCallback {
    void updateSearchResults(GitHubJobs jobs);
}
