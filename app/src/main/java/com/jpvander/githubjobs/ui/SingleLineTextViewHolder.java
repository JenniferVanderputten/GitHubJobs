package com.jpvander.githubjobs.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jpvander.githubjobs.R;

/**
 * Created by jenva on 2/4/2016.
 */
public class SingleLineTextViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public SingleLineTextViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.textView);
    }
}
