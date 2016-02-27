package com.jpvander.githubjobs.activities.searches;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapters.JobDetailsViewAdapter;

public class ViewJobDetailsFragment extends BaseFragment {

    private static final String TITLE = "Job Details";

    private JobDetailsViewAdapter jobDetailsAdapter;

    @SuppressWarnings("unused")
    public ViewJobDetailsFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_job_details, container, false);

        if (null == jobDetailsAdapter) { jobDetailsAdapter = new JobDetailsViewAdapter(); }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(jobDetailsAdapter);
        recyclerView.addItemDecoration(divider);

        return view;
    }

    public void setJobsSelected(GitHubJobs jobsSelected) {
        if (null == jobDetailsAdapter) { jobDetailsAdapter = new JobDetailsViewAdapter(); }
        jobDetailsAdapter.setJobsSelected(jobsSelected);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        jobDetailsAdapter = null;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
