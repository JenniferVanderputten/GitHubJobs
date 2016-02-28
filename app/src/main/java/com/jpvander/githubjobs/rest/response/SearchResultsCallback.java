package com.jpvander.githubjobs.rest.response;

import com.jpvander.githubjobs.datasets.data.GitHubJobs;

public interface SearchResultsCallback {
    void updateSearchResults(GitHubJobs jobs);
}
