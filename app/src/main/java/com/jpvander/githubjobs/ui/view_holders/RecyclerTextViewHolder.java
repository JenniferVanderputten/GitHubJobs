package com.jpvander.githubjobs.ui.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jpvander.githubjobs.R;

public class RecyclerTextViewHolder extends RecyclerView.ViewHolder {

    public final TextView textView;

    public RecyclerTextViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.textView);
    }
}
