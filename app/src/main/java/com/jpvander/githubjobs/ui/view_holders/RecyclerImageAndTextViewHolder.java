package com.jpvander.githubjobs.ui.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.jpvander.githubjobs.R;

public class RecyclerImageAndTextViewHolder extends RecyclerView.ViewHolder {

    public final TextView textView;
    public final ImageView imageView;

    public RecyclerImageAndTextViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.imageView);
        this.textView = (TextView) view.findViewById(R.id.textView);
    }
}
