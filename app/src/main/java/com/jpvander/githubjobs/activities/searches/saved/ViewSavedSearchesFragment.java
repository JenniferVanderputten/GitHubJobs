package com.jpvander.githubjobs.activities.searches.saved;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.*;
import com.jpvander.githubjobs.ui.GitHubJobsView;

public class ViewSavedSearchesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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
        jobSearches.add(0, new GitHubJob("PHP", "San Francisco"));

        new GitHubJobsView(
                getActivity(), (RecyclerView) savedSearchesView.findViewById(R.id.recycler), jobSearches);

        return savedSearchesView;
    }

    // TODO: hook method into UI event
    public void onSavedSearchesItemPressed(Uri uri) {
        if (mListener != null) {
            mListener.onViewSavedSearchesInteraction(uri);
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
        void onViewSavedSearchesInteraction(Uri uri);
    }
}
