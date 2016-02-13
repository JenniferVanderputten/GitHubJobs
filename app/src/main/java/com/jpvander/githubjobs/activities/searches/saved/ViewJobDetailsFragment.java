package com.jpvander.githubjobs.activities.searches.saved;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.GitHubJob;
import com.jpvander.githubjobs.datasets.GitHubJobs;
import com.jpvander.githubjobs.ui.SearchView;
import com.jpvander.githubjobs.ui.JobDetailsViewAdapter;

public class ViewJobDetailsFragment extends Fragment {

    private static final float PADDING_DP = 10.0f;

    private GitHubJob jobSelected;

    public ViewJobDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View jobDetailsView = inflater.inflate(R.layout.fragment_view_job_details, container, false);

        if (null == jobSelected) {
            jobSelected = new GitHubJob();
            jobSelected.setTitle("Product Tester");
            jobSelected.setCompany("ACME");
            jobSelected.setLocation("Monument Valley, UT");
            jobSelected.setDescription("Looking for a bright but accident-prone coyote for product testing");
        }

        GitHubJobs jobsToDisplay = new GitHubJobs();
        jobsToDisplay.add(jobSelected);

        new SearchView(getActivity(),
                (RecyclerView) jobDetailsView.findViewById(R.id.recycler),
                new JobDetailsViewAdapter(this, jobsToDisplay));

        return jobDetailsView;
    }

    public void setJobSelected(GitHubJob job) {
        this.jobSelected = job;
    }
}
