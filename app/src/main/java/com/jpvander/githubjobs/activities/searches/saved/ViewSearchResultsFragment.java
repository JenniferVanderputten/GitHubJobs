package com.jpvander.githubjobs.activities.searches.saved;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.ui.GitHubJobsView;
import com.jpvander.githubjobs.ui.SearchResultsViewAdapter;
import com.loopj.android.http.RequestParams;
import com.jpvander.githubjobs.datasets.*;

public class ViewSearchResultsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private GitHubJob jobRequested;
    private GitHubJobs jobsFound;

    public ViewSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View searchResultsView = inflater.inflate(R.layout.fragment_view_search_results, container, false);
        
        if (null == jobRequested) {
            jobRequested = new GitHubJob();
        }

        jobsFound = new GitHubJobs();

        GitHubJobsView jobsView = new GitHubJobsView(getActivity(),
                (RecyclerView) searchResultsView.findViewById(R.id.recycler),
                new SearchResultsViewAdapter(this, jobsFound));

        RequestParams params = new RequestParams();
        params.add("description", jobRequested.getDescription());
        params.add("location", jobRequested.getLocation());
        JsonResponseHandler getPositionsResponseHandler = new JsonResponseHandler(
                new OnGetPositionsResponseCallback(jobsView.getViewAdapter()));
        AsyncRestClient.getPositions(params, getPositionsResponseHandler);

        return searchResultsView;
    }

    public void onSearchResultsItemPressed(GitHubJob job) {
        if (mListener != null) {
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
