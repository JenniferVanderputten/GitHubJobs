package com.jpvander.githubjobs.activities.searches.saved;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.jpvander.githubjobs.R;

public class ViewSearchesActivity extends AppCompatActivity
        implements ViewSavedSearchesFragment.OnFragmentInteractionListener,
        ViewSearchResultsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ViewSavedSearchesFragment savedSearchesFragment = new ViewSavedSearchesFragment();
            savedSearchesFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, savedSearchesFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO: Uncomment getMenuInflater() when you add the app's settings activity (or add a custom overflow item)
        //getMenuInflater().inflate(R.menu.menu_view_searches, menu);
        return true;
    }

    public void onViewSavedSearchesInteraction(Uri uri) {
        //TODO: Add interaction for ViewSavedSearchesFragment
    }

    public void onViewSearchResultsInteraction(Uri uri) {
        //TODO: Add interaction for ViewSearchResultsFragment
    }
}
