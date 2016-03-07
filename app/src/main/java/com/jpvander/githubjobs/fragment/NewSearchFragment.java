package com.jpvander.githubjobs.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.activity.ViewSearchesActivity;
import com.jpvander.githubjobs.dataset.data.GitHubJob;
import com.jpvander.githubjobs.dataset.helper.SavedSearchesDbHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSearchFragment extends BaseFragment {

    private String title;
    private static final boolean SHOULD_SHOW_SEARCH = false;
    private static final boolean SHOULD_SHOW_LOCATION = true;

    private OnFragmentInteractionListener interactionListener;
    private HashMap<String, TextView> contentMap;
    private HashMap<String, String> hintMap;

    @SuppressWarnings("unused")
    @SuppressLint("unused")
    public NewSearchFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Allow user to (optionally) name the search
        final Activity activity = getActivity();
        Context context = container.getContext();
        final View view = inflater.inflate(R.layout.fragment_new_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.fns_search_button);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.fns_table_layout);
        Resources resources = getResources();
        title = resources.getString(R.string.new_search_title);
        ((ViewSearchesActivity) getActivity()).setActionBarTitle(getTitle());
        if (null == contentMap) { contentMap = new HashMap<>(); }

        hintMap = new HashMap<>();
        hintMap.put(GitHubJob.LOCATION_LABEL, resources.getString(R.string.input_location_hint));
        hintMap.put(GitHubJob.DESCRIPTION_LABEL, resources.getString(R.string.input_description_hint));

        tableLayout.addView(addInputField(context, GitHubJob.LOCATION_LABEL));
        tableLayout.addView(addInputField(context, GitHubJob.DESCRIPTION_LABEL));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GitHubJob newJob = new GitHubJob();

                if (null != contentMap) {
                    if (contentMap.containsKey(GitHubJob.LOCATION_LABEL)) {
                        newJob.setLocation(contentMap.get(GitHubJob.LOCATION_LABEL).getText().toString());
                    }

                    if (contentMap.containsKey(GitHubJob.DESCRIPTION_LABEL)) {
                        newJob.setDescription(contentMap.get(GitHubJob.DESCRIPTION_LABEL).getText().toString());
                    }
                }

                ArrayList<String> jobFields = new ArrayList<>();
                jobFields.add(newJob.getDescription());
                jobFields.add(newJob.getLocation());
                newJob.setDisplayTitle(jobFields);
                SavedSearchesDbHelper savedSearchesDbHelper = new SavedSearchesDbHelper(activity);
                //TODO: Prevent saving the same search twice
                savedSearchesDbHelper.insertRow(newJob);
                savedSearchesDbHelper.close();
                interactionListener.onNewSearchInteraction(newJob);
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
        contentMap = null;
        hintMap = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outputState) {
        if (null != outputState) {
            for (String uiField : contentMap.keySet()) {
                outputState.putString(uiField, contentMap.get(uiField).getText().toString());
            }
        }

        super.onSaveInstanceState(outputState);
    }

    @Override
    public void onActivityCreated(Bundle inputState) {
        super.onActivityCreated(inputState);

        if (null != inputState) {
            for (String uiField : contentMap.keySet()) {
                TextView textView = contentMap.get(uiField);

                if (null != textView) {
                    textView.setText(inputState.getString(uiField));
                }
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onNewSearchInteraction(GitHubJob job);
    }

    private TableRow addInputField(Context context, String label) {
        float displayScale = context.getResources().getDisplayMetrics().density;
        float margin = context.getResources().getDimension(R.dimen.small_margin);
        int paddingPx = (int) (margin * displayScale);

        TableRow tableRow = new TableRow(context);
        tableRow.setPaddingRelative(0, paddingPx, 0, 0);

        TextView labelView = new TextView(context);
        labelView.setText(getResources().getString(R.string.label_formatter, label));
        labelView.setPaddingRelative(0, 0, paddingPx, 0);
        labelView.setGravity(Gravity.CENTER_VERTICAL);

        EditText contentView = new EditText(context);
        contentView.setHint(hintMap.get(label));

        if (null == contentMap) { contentMap = new HashMap<>(); }
        contentMap.put(label, contentView);

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(rowParams);

        TableRow.LayoutParams labelParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        labelView.setLayoutParams(labelParams);

        TableRow.LayoutParams contentParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        contentView.setLayoutParams(contentParams);

        tableRow.addView(labelView);
        tableRow.addView(contentView);

        return tableRow;
    }

    private String getTitle() {
        return title;
    }

    @Override
    public boolean shouldShowMenuSearch() {
        return SHOULD_SHOW_SEARCH;
    }

    @Override
    public boolean shouldShowMenuLocation() {
        return SHOULD_SHOW_LOCATION;
    }

    @Override
    public void setLocality(String locality) {
        if (null != locality) {
            TextView locationTextView = contentMap.get(GitHubJob.LOCATION_LABEL);
            locationTextView.setText(locality);
        }
    }
}
