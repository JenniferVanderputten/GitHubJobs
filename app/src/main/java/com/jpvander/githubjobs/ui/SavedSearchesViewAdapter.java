package com.jpvander.githubjobs.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpvander.githubjobs.R;

import java.util.ArrayList;

/**
 * Created by jenva on 2/4/2016.
 */
public class SavedSearchesViewAdapter extends RecyclerView.Adapter<SingleLineTextViewHolder> {

    public ArrayList<String> savedSearches;

    public SavedSearchesViewAdapter(ArrayList<String> savedSearches) {
        this.savedSearches = savedSearches;
    }

    @Override
    public SingleLineTextViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View savedSearchesView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.saved_search_item, parent, false);

        return (new SingleLineTextViewHolder(savedSearchesView));
    }

    @Override
    public void onBindViewHolder(SingleLineTextViewHolder holder, int position) {
        holder.textView.setText(savedSearches.get(position));
    }

    @Override
    public int getItemCount() {
        return savedSearches.size();
    }

    public void updateDataSet(ArrayList<String> savedSearches) {
        this.savedSearches = savedSearches;
        this.notifyDataSetChanged();
    }
}
