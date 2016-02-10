package com.jpvander.githubjobs.activities.searches.saved;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.ui.GitHubJobsView;
import com.loopj.android.http.RequestParams;
import com.jpvander.githubjobs.datasets.*;

public class ViewSearchResultsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ViewSearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View searchResultsView = inflater.inflate(R.layout.fragment_view_search_results, container, false);

        GitHubJobs jobsFound = new GitHubJobs();
        jobsFound.add(0, new GitHubJob("PHP", "San Francisco"));

        GitHubJobsView jobsView = new GitHubJobsView(
                getActivity(), (RecyclerView) searchResultsView.findViewById(R.id.recycler), jobsFound);

        RequestParams params = new RequestParams();
        params.add("description", "php");
        params.add("location", "San Francisco");
        JsonResponseHandler getPositionsResponseHandler = new JsonResponseHandler(
                new OnGetPositionsResponseCallback(jobsView.getViewAdapter()));
        AsyncRestClient.getPositions(params, getPositionsResponseHandler);

        return searchResultsView;
    }

    public void onSearchResultsItemPressed(Uri uri) {
        if (mListener != null) {
            mListener.onViewSearchResultsInteraction(uri);
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

    public interface OnFragmentInteractionListener {
        void onViewSearchResultsInteraction(Uri uri);
    }
}
