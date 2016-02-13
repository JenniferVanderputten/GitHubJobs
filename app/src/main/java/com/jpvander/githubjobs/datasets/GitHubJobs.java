package com.jpvander.githubjobs.datasets;

import java.util.HashMap;

public class GitHubJobs
{
    private final HashMap<Integer, GitHubJob> savedSearches;

    public GitHubJobs() {
        this.savedSearches = new HashMap<>();
    }

    public void add(int position, GitHubJob gitHubJob) {
        this.savedSearches.put(position, gitHubJob);
    }

    public void add(GitHubJob gitHubJob) {
        this.savedSearches.put(this.savedSearches.size(), gitHubJob);
    }

    public GitHubJob get(int position) {
        return this.savedSearches.get(position);
    }

    public int size() {
        return savedSearches.size();
    }
}
