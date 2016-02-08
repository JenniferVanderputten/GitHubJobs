package com.jpvander.githubjobs.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.rest.request.AsyncRestClient;
import com.jpvander.githubjobs.rest.response.JsonResponseHandler;
import com.jpvander.githubjobs.rest.response.OnGetPositionsResponseCallback;
import com.jpvander.githubjobs.ui.DividerItemDecoration;
import com.jpvander.githubjobs.ui.SavedSearchesViewAdapter;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class ViewSearchesActivity extends AppCompatActivity {

    private static final float VERTICAL_LIST_ITEM_PADDING_IN_DP = 20.0f;

    private RecyclerView savedSearchesRecyclerView;
    private SavedSearchesViewAdapter savedSearchesRecyclerAdapter;
    private RecyclerView.LayoutManager savedSearchesLayoutManager;
    private JsonResponseHandler getPositionsResponseHandler;
    private OnGetPositionsResponseCallback onGetPositionsResponseCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);
        float displayScale = getResources().getDisplayMetrics().density;
        int verticalListItemPaddingInPx = (int) (VERTICAL_LIST_ITEM_PADDING_IN_DP*displayScale + 0.5f);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        savedSearchesRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        savedSearchesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        savedSearchesRecyclerView.setLayoutManager(savedSearchesLayoutManager);
        savedSearchesRecyclerAdapter = new SavedSearchesViewAdapter(new ArrayList<String>());
        savedSearchesRecyclerView.setAdapter(savedSearchesRecyclerAdapter);
        savedSearchesRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, 0, 0, verticalListItemPaddingInPx, verticalListItemPaddingInPx,
                        LinearLayoutManager.VERTICAL));

        RequestParams params = new RequestParams();
        params.add("description", "php");
        params.add("location", "San Francisco");
        onGetPositionsResponseCallback = new OnGetPositionsResponseCallback(savedSearchesRecyclerAdapter);
        getPositionsResponseHandler = new JsonResponseHandler(onGetPositionsResponseCallback);
        AsyncRestClient.getPositions(params, getPositionsResponseHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO: Uncomment getMenuInflater() when you add the app's settings activity (or add a custom overflow item)
        //getMenuInflater().inflate(R.menu.menu_view_searches, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
