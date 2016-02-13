package com.jpvander.githubjobs.activities.searches;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.ui.search_results.SearchResultsView;
import com.jpvander.githubjobs.ui.search_results.SearchResultsViewAdapter;
import com.loopj.android.http.RequestParams;
import com.jpvander.githubjobs.datasets.*;

public class ViewSearchResultsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GitHubJob jobRequested;

    @SuppressWarnings("unused")
    public ViewSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_view_search_results, container, false);

        if (null == jobRequested) {
            jobRequested = new GitHubJob();
        }

        SearchResultsView searchResultsView = new SearchResultsView(getActivity(),
                (RecyclerView) fragmentView.findViewById(R.id.recycler),
                new SearchResultsViewAdapter(this));

        JsonResponseHandler getPositionsResponseHandler = new JsonResponseHandler(
                new OnGetPositionsResponseCallback(searchResultsView.getViewAdapter()));

        RequestParams params = jobRequested.getRequestParams();
        AsyncRestClient.getPositions(params, getPositionsResponseHandler);

        return fragmentView;
    }

    public void onSearchResultsItemPressed(GitHubJob job) {
        Log.d("GitHubJobs", "ViewSearchResultsFragment::onSearchResultsItemPressed");
        if (null == mListener) { Log.d("GitHubJobs", "mListener is NULL"); }
        if (null == job) { Log.d("GitHubJobs", "job is NULL"); }

        if (null != mListener && null != job) {
            mListener.onViewSearchResultsInteraction(job);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setJobRequested(GitHubJob jobRequested) {
        this.jobRequested = jobRequested;
    }

    public interface OnFragmentInteractionListener {
        void onViewSearchResultsInteraction(GitHubJob job);
    }
}