package com.jpvander.githubjobs.activities.searches;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.helpers.SavedSearchesDbHelper;
import com.jpvander.githubjobs.datasets.data.GitHubJob;
import com.jpvander.githubjobs.datasets.data.GitHubJobs;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.ui.adapters.SavedSearchesViewAdapter;

public class ViewSavedSearchesFragment extends BaseFragment {

    private static final String TITLE = "Saved Searches";

    private OnFragmentInteractionListener interactionListener;


    @SuppressWarnings("unused")
    public ViewSavedSearchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Add delete button to each item
        Activity activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_view_saved_searches, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interactionListener.onViewSavedSearchesInteraction(null, true);
            }
        });

        SavedSearchesDbHelper dbHelper = new SavedSearchesDbHelper(container.getContext());

        if (0 >= dbHelper.getRowCount()) {
            dbHelper.insertRow(new GitHubJob("PHP", "San Francisco"));
        }

        GitHubJobs jobSearches = dbHelper.getSavedSearches();
        dbHelper.close();

        SavedSearchesViewAdapter savedSearchesAdapter = new SavedSearchesViewAdapter(interactionListener, jobSearches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(savedSearchesAdapter);
        recyclerView.addItemDecoration(divider);

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
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onViewSavedSearchesInteraction(GitHubJob job, boolean newSearch);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
