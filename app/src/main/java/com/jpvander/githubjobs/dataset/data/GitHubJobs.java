package com.jpvander.githubjobs.dataset.data;

import java.util.ArrayList;

public class GitHubJobs
{
    private final ArrayList<GitHubJob> jobs;

    public GitHubJobs() {
        this.jobs = new ArrayList<>();
    }

    public void add(GitHubJob job) {
        jobs.add(job);
    }

    public void remove(int position) { jobs.remove(position); }

    public GitHubJob get(int position) {
        if (position < size()) {
            return jobs.get(position);
        }
        else {
            return new GitHubJob();
        }
    }

    public int size() {
        return jobs.size();
    }
}
