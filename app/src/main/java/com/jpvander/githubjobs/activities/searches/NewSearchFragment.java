package com.jpvander.githubjobs.activities.searches;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NewSearchFragment extends BaseFragment {

    private static final String TITLE = "New Search";
    private static final int LOCATION_ACCESS_REQUEST = 111;

    private OnFragmentInteractionListener interactionListener;
    private HashMap<String, TextView> contentMap;
    private FloatingActionButton fab;
    private ProgressDialog spinner;

    @SuppressWarnings("unused")
    public NewSearchFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        //TODO: Add contextual search to keyboard for searchButton onClick shortcut
        Context context = container.getContext();
        final View view = inflater.inflate(R.layout.fragment_new_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        if (null == contentMap) {
            contentMap = new HashMap<>();
            //TODO: Make an enum for these content-tags
            contentMap.put("location", null);
            contentMap.put("description", null);
        }

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

        spinner = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        final int accessPerm = ContextCompat.checkSelfPermission(getActivity(), permissions[0]);
        final boolean shouldShowRationale = shouldShowRequestPermissionRationale(permissions[0]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (accessPerm != PackageManager.PERMISSION_GRANTED && shouldShowRationale) {
                disableFAB();
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setTitle("Getting location...");
                spinner.setCancelable(false);
                spinner.show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (accessPerm == PackageManager.PERMISSION_GRANTED) {
                        setLocationFromServices();
                    }
                    else {
                        if (shouldShowRationale) {
                            // TODO: Add settings activity and add grant-permissions option so user can change later
                            spinner.cancel();
                            disableFAB();
                        }
                        else {
                            requestPermissions(permissions, LOCATION_ACCESS_REQUEST);
                        }
                    }
                }
                else {
                    setLocationFromServices();
                }
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
    public void onStop() {
        super.onStop();
        spinner.cancel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
        contentMap = null;
        spinner = null;
        fab = null;
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

    private TableRow createTableRowWithWritableContent(Context context, String label,
                                                      String contentHint, String contentTag) {
        float displayScale = context.getResources().getDisplayMetrics().density;
        float margin = context.getResources().getDimension(R.dimen.container_horizontal_margin);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] results) {
        switch (requestCode) {
            case LOCATION_ACCESS_REQUEST: {
                if (0 >= results.length && results[0] == PackageManager.PERMISSION_GRANTED) {
                    setLocationFromServices();
                }
                else {
                    disableFAB();
                }
            }
        }

        spinner.cancel();
    }

    private void setLocationFromServices() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        Location location;
        String locality;

        try {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (null == location) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (null != location) {
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);

                if (null == addresses || 0 >= addresses.size()) {
                    //TODO: Pop up an error
                }
                else {
                    Address address = addresses.get(0);
                    locality = address.getLocality();
                    TextView locationTextView = contentMap.get("location");
                    locationTextView.setText(locality);
                }
            }
        }
        catch (SecurityException exception) {
            Log.e("GitHubJobs", "Security exception getting location: " + exception.getMessage());
            //TODO: Pop up an error
        }
        catch (IOException exception) {
            Log.e("GitHubJobs", "IO exception getting location: " + exception.getMessage());
            //TODO: Pop up an error
        }
        catch (IllegalArgumentException exception) {
            Log.e("GitHubJobs", "Illegal argument exception getting location: " + exception.getMessage());
            //TODO: Pop up an error
        }
        catch (Exception exception) {
            Log.e("GitHubJobs", "Exception getting location: " + exception.getMessage());
            //TODO: Pop up an error
        }
        finally {
            spinner.cancel();
        }
    }

    private void disableFAB() {
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
    }
}
