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
import android.widget.TextView;

import com.google.gson.Gson;
import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.helpers.SearchResultsDbHelper;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.rest.response.SearchResultsCallback;
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
    private SearchResultsViewAdapter adapter;

    @SuppressWarnings("unused")
    public ViewSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (null != savedState) {
            String jobRequestedAsJSON = savedState.getString("jobRequested");
            jobRequested = new Gson().fromJson(jobRequestedAsJSON, GitHubJob.class);
            jobRequestedChanged = savedState.getBoolean("jobRequestedChanged");
            title = savedState.getString("title");
            searchResultsDbHelper = new SearchResultsDbHelper(getContext());
            jobsFound = searchResultsDbHelper.getSearchResults(jobRequested.getSavedSearchId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_search_results, container, false);
        Context context = getContext();
        if (null == searchResultsDbHelper) { searchResultsDbHelper = new SearchResultsDbHelper(context); }
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        final TextView emptyView = (TextView) view.findViewById(R.id.empty_view);

        spinner = new ProgressDialog(activity, ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Searching GitHub Jobs...");
        spinner.setCancelable(true);

        adapter = new SearchResultsViewAdapter(interactionListener,
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

                        if (null != jobRequested) {
                            if (null != jobs && 0 < jobs.size()) {
                                jobsFound = jobs;
                                searchResultsDbHelper.saveSearchResults(jobRequested.getSavedSearchId(), jobs);
                            }
                            else {
                                jobsFound = searchResultsDbHelper.getSearchResults(jobRequested.getSavedSearchId());
                            }
                        }

                        if (null == jobsFound || 0 >= jobsFound.size()) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            adapter.updateDataSet(jobsFound);
                        }
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
        }

        if (null == jobsFound || 0 >= jobsFound.size()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            adapter.updateDataSet(jobsFound);
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
        adapter = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outputState) {
        String jobRequestedAsJSON = new Gson().toJson(jobRequested);
        outputState.putString("jobRequested", jobRequestedAsJSON);
        outputState.putString("title", title);
        outputState.putBoolean("jobRequestedChanged", jobRequestedChanged);
        super.onSaveInstanceState(outputState);
    }

    @Override
    public void onActivityCreated(Bundle inputState) {
        super.onActivityCreated(inputState);
        adapter.updateDataSet(jobsFound);
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
