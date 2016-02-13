package com.jpvander.githubjobs.ui.search_results;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.R;
import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;

public class SearchResultsView {

    private final SearchResultsViewAdapter viewAdapter;

    public SearchResultsView(Activity activity, RecyclerView view, SearchResultsViewAdapter viewAdapter) {

        float displayScale = activity.getResources().getDisplayMetrics().density;
        int paddingPx = (int) (activity.getResources()
                .getDimension(R.dimen.list_item_horizontal_margin) * displayScale);

        view.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        this.viewAdapter = viewAdapter;
        view.setAdapter(viewAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        divider.setTopPadding(paddingPx);
        divider.setBottomPadding(paddingPx);
        view.addItemDecoration(divider);
    }

    public SearchResultsViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }
}
