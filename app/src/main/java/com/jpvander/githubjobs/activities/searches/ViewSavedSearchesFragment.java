package com.jpvander.githubjobs.activities.searches;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.*;
import com.jpvander.githubjobs.ui.saved_searches.SavedSearchesView;
import com.jpvander.githubjobs.ui.saved_searches.SavedSearchesViewAdapter;

public class ViewSavedSearchesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @SuppressWarnings("unused")
    public ViewSavedSearchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View savedSearchesView = inflater.inflate(R.layout.fragment_view_saved_searches, container, false);

        FloatingActionButton fab = (FloatingActionButton) savedSearchesView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GitHubJobs jobSearches = new GitHubJobs();
        //TODO: get from disk (need to do saves)
        jobSearches.add(0, new GitHubJob("PHP", "San Francisco"));
        jobSearches.add(1, new GitHubJob("PHP", "New York"));
        jobSearches.add(2, new GitHubJob("PHP", "Amsterdam"));

        new SavedSearchesView(getActivity(),
                (RecyclerView) savedSearchesView.findViewById(R.id.recycler),
                new SavedSearchesViewAdapter(this, jobSearches));

        return savedSearchesView;
    }

    public void onSavedSearchesItemPressed(GitHubJob job) {
        Log.d("GitHubJobs", "ViewSavedSearchesFragment::onSavedSearchesItemPressed");
        if (null == mListener) { Log.d("GitHubJobs", "mListener is NULL"); }
        if (null == job) { Log.d("GitHubJobs", "job is NULL"); }

        if (null != mListener && null != job) {
            mListener.onViewSavedSearchesInteraction(job);
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

/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == savedInstanceState) {
            // job = (GitHubJob) savedInstanceState.getSerializable("job");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle exitState) {
        super.onSaveInstanceState(exitState);
        // exitState.putSerializable("job", (Serializable) job);
    }
*/

    public interface OnFragmentInteractionListener {
        void onViewSavedSearchesInteraction(GitHubJob job);
    }
}