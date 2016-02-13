package com.jpvander.githubjobs.ui.saved_searches;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jpvander.githubjobs.ui.graphics.DividerItemDecoration;
import com.jpvander.githubjobs.R;

public class SavedSearchesView {

    public SavedSearchesView(Activity activity, RecyclerView view, SavedSearchesViewAdapter viewAdapter) {

        float displayScale = activity.getResources().getDisplayMetrics().density;
        int paddingPx = (int) (activity.getResources()
                .getDimension(R.dimen.list_item_horizontal_margin) * displayScale);

        view.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(viewAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(activity);
        divider.setTopPadding(paddingPx);
        divider.setBottomPadding(paddingPx);
        view.addItemDecoration(divider);
    }
}
