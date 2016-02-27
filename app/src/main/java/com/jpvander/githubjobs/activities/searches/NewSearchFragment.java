package com.jpvander.githubjobs.activities.searches;

import android.content.Context;
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

import com.jpvander.githubjobs.activities.BaseFragment;
import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.datasets.data.GitHubJob;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSearchFragment extends BaseFragment {

    private static final String TITLE = "New Search";

    private OnFragmentInteractionListener interactionListener;
    private HashMap<String, TextView> contentMap;

    @SuppressWarnings("unused")
    public NewSearchFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        Context context = container.getContext();
        final View view = inflater.inflate(R.layout.fragment_new_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        if (null == contentMap) { contentMap = new HashMap<>(); }

        TableRow tableRow = createTableRowWithWritableContent(context, "Location:",
                "City, Zip Code, or Remote", "location");
        tableLayout.addView(tableRow);

        tableRow = createTableRowWithWritableContent(context, "Description:", "e.g. PHP or JAVA",
                "description");
        tableLayout.addView(tableRow);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GitHubJob newJob = new GitHubJob();

                if (null != contentMap) {
                    if (contentMap.containsKey("location")) {
                        newJob.setLocation(contentMap.get("location").getText().toString());
                    }

                    if (contentMap.containsKey("description")) {
                        newJob.setDescription(contentMap.get("description").getText().toString());
                    }
                }

                ArrayList<String> jobFields = new ArrayList<>();
                jobFields.add(newJob.getDescription());
                jobFields.add(newJob.getLocation());
                newJob.setDisplayTitle(jobFields);
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
    }

    public interface OnFragmentInteractionListener {
        void onNewSearchInteraction(GitHubJob job);
    }

    private TableRow createTableRowWithWritableContent(Context context, String label,
                                                      String contentHint, String contentTag) {
        float displayScale = context.getResources().getDisplayMetrics().density;
        float margin = context.getResources().getDimension(R.dimen.list_item_horizontal_margin);
        int paddingPx = (int) (margin * displayScale);

        TableRow tableRow = new TableRow(context);
        tableRow.setPadding(0, paddingPx, 0, 0);

        TextView labelView = new TextView(context);
        labelView.setText(label);
        labelView.setPadding(0, 0, paddingPx, 0);
        labelView.setGravity(Gravity.CENTER_VERTICAL);

        EditText contentView = new EditText(context);
        contentView.setHint(contentHint);

        if (null == contentMap) { contentMap = new HashMap<>(); }
        contentMap.put(contentTag, contentView);

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

    @Override
    public String getTitle() {
        return TITLE;
    }
}
