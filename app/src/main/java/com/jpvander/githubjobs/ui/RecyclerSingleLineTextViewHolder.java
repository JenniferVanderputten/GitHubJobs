package com.jpvander.githubjobs.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jpvander.githubjobs.R;

public class RecyclerSingleLineTextViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public RecyclerSingleLineTextViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.textView);
    }
}
