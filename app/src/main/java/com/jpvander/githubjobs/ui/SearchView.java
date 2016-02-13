package com.jpvander.githubjobs.ui;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SearchView {

    private static final int ORIENTATION = LinearLayoutManager.VERTICAL;
    private static final float PADDING_DP = 20.0f;

    private final SearchViewAdapter viewAdapter;

    public SearchView(Activity activity, RecyclerView view, SearchViewAdapter viewAdapter) {

        float displayScale = activity.getResources().getDisplayMetrics().density;
        int paddingPx = (int) (PADDING_DP * displayScale + 0.5f);

        view.setLayoutManager(
                new LinearLayoutManager(activity, ORIENTATION, false));
        this.viewAdapter = viewAdapter;
        view.setAdapter(viewAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        divider.setTopPadding(paddingPx);
        divider.setBottomPadding(paddingPx);
        view.addItemDecoration(divider);
    }

    public SearchViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }
}
