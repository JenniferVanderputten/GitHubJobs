package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activity.ViewSearchesActivity;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.helper.SavedSearchesDbHelper;


public class NewSearchFragment extends BaseFragment {

    private static final boolean SHOULD_SHOW_LOCATION = true;

    private String title;
    private OnFragmentInteractionListener interactionListener;
    private GitHubJob newJob;
    private TextView locationInput;
    private TextView descriptionInput;
    private CheckBox fullTimeInput;
    private CheckBox partTimeInput;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public NewSearchFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Allow user to (optionally) name the search
        final Activity activity = getActivity();
        final View view = inflater.inflate(R.layout.fragment_new_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.new_search_submit_button);
        Resources resources = getResources();
        title = resources.getString(R.string.new_search_title);
        ((ViewSearchesActivity) getActivity()).setActionBarTitle(getTitle());
        newJob = new GitHubJob();

        locationInput = (TextView) view.findViewById(R.id.new_search_location_value);
        descriptionInput = (TextView) view.findViewById(R.id.new_search_description_value);
        fullTimeInput = (CheckBox) view.findViewById(R.id.new_search_full_time_value);
        partTimeInput = (CheckBox) view.findViewById(R.id.new_search_part_time_value);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newJob.setLocation(locationInput.getText().toString());
                newJob.setDescription(descriptionInput.getText().toString());
                newJob.setFullAndPartTime(fullTimeInput.isChecked(), partTimeInput.isChecked());
                SavedSearchesDbHelper savedSearchesDbHelper = new SavedSearchesDbHelper(activity);
                //TODO: Prevent saving the same search twice
                savedSearchesDbHelper.insertRow(newJob);
                savedSearchesDbHelper.close();
                interactionListener.onNewSearchInteraction(newJob);
                locationInput.setText("");
                descriptionInput.setText("");
                fullTimeInput.setChecked(true);
                partTimeInput.setChecked(true);
            }
        });

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

    @Override
    public void onSaveInstanceState(Bundle outputState) {
        if (null != outputState) {
            outputState.putString(GitHubJob.LOCATION_LABEL, locationInput.getText().toString());
            outputState.putString(GitHubJob.DESCRIPTION_LABEL, descriptionInput.getText().toString());
            outputState.putBoolean(GitHubJob.FULL_TIME_LABEL, fullTimeInput.isChecked());
            outputState.putBoolean(GitHubJob.PART_TIME_LABEL, partTimeInput.isChecked());
        }

        super.onSaveInstanceState(outputState);
    }

    @Override
    public void onActivityCreated(Bundle inputState) {
        super.onActivityCreated(inputState);

        if (null != inputState) {
            locationInput.setText(inputState.getString(GitHubJob.LOCATION_LABEL));
            descriptionInput.setText(inputState.getString(GitHubJob.DESCRIPTION_LABEL));
            fullTimeInput.setChecked(inputState.getBoolean(GitHubJob.FULL_TIME_LABEL));
            partTimeInput.setChecked(inputState.getBoolean(GitHubJob.PART_TIME_LABEL));
        }
    }

    public interface OnFragmentInteractionListener {
        void onNewSearchInteraction(GitHubJob job);
    }

    private String getTitle() {
        return title;
    }

    @Override
    public boolean shouldShowMenuLocation() {
        return SHOULD_SHOW_LOCATION;
    }

    @Override
    public void setLocality(String locality) {
        if (null != locality) {
            locationInput.setText(locality);
        }
    }
}
