package com.jpvander.githubjobs.datasets.data;

import java.util.HashMap;

public class GitHubJobs
{
    private final HashMap<Integer, GitHubJob> jobs;

    public GitHubJobs() {
        this.jobs = new HashMap<>();
    }

    public void add(GitHubJob gitHubJob) {
        this.jobs.put(this.jobs.size(), gitHubJob);
    }

    public GitHubJob get(int position) {
        if (jobs.containsKey(position)) {
            return this.jobs.get(position);
        }
        else {
            return new GitHubJob();
        }
    }

    public int size() {
        return jobs.size();
    }
}
