package com.jpvander.githubjobs.activities.searches;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.helpers.SearchResultsDbHelper;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.ui.adapters.SearchResultsCallback;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapters.SearchResultsViewAdapter;

import java.util.ArrayList;


public class ViewSearchResultsFragment extends BaseFragment {

    private OnFragmentInteractionListener interactionListener;
    private JsonResponseHandler getPositionsResponseHandler;
    private GitHubJob jobRequested;
    private boolean jobRequestedChanged = false;
    private GitHubJobs jobsFound;
    private ProgressDialog spinner;
    private SearchResultsDbHelper searchResultsDbHelper;
    private String title;

    @SuppressWarnings("unused")
    public ViewSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Make toolbar title the job requested title
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_search_results, container, false);
        Context context = container.getContext();
        searchResultsDbHelper = new SearchResultsDbHelper(context);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        spinner = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Searching GitHub Jobs...");
        spinner.setCancelable(true);

        final SearchResultsViewAdapter adapter = new SearchResultsViewAdapter(
                interactionListener,
                context.getResources().getDisplayMetrics().density);

        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);

        OnGetPositionsResponseCallback callback = new OnGetPositionsResponseCallback(
                new SearchResultsCallback() {
                    @Override
                    public void updateSearchResults(GitHubJobs jobs) {
                        spinner.cancel();

                        if (null == jobs) {
                            jobsFound = searchResultsDbHelper.getSearchResults();
                        }
                        else {
                            jobsFound = jobs;
                            searchResultsDbHelper.saveSearchResults(jobs);
                        }

                        //TODO: Indicate no jobs found if jobsFound is empty
                        adapter.updateDataSet(jobsFound);
                    }
                }
        );
        getPositionsResponseHandler = new JsonResponseHandler(callback);

        if (null != jobRequested) {
            if (jobRequestedChanged) {
                spinner.show();
                jobRequestedChanged = false;
                AsyncRestClient.getPositions(jobRequested.getRequestParams(), getPositionsResponseHandler);
            }
            else {
                //TODO: Indicate no jobs found if jobsFound is empty
                adapter.updateDataSet(jobsFound);
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + R.string.must_implement_OFIL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        spinner.cancel();
        searchResultsDbHelper.close();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
        getPositionsResponseHandler = null;
        jobRequested = null;
        jobsFound = null;
        spinner = null;
        searchResultsDbHelper = null;
        title = null;
    }

    public void setJobRequested(GitHubJob jobRequested) {
        this.jobRequested = jobRequested;
        ArrayList<String> jobFields = new ArrayList<>();
        jobFields.add(jobRequested.getModifiedDescription());
        jobFields.add(jobRequested.getModifiedLocation());
        jobRequested.setDisplayTitle(jobFields);
        title = jobRequested.getDisplayTitle();
        jobRequestedChanged = true;
    }

    public interface OnFragmentInteractionListener {
        void onViewSearchResultsInteraction(GitHubJob job);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
