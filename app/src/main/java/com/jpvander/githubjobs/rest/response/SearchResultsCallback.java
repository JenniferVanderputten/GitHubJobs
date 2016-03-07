package com.jpvander.githubjobs.rest.response;

import com.jpvander.githubjobs.dataset.data.GitHubJobs;

public interface SearchResultsCallback {
    void updateSearchResults(GitHubJobs jobs);
}
