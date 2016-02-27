package com.jpvander.githubjobs.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.jpvander.githubjobs.R;

public class RecyclerImageAndTextViewHolder extends RecyclerView.ViewHolder {

    private static final float PREFERRED_IMAGE_WIDTH = 140.0f;

    public final TextView textView;
    public final ImageView imageView;
    private final float preferredImageWidth;

    public RecyclerImageAndTextViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.imageView);
        this.textView = (TextView) view.findViewById(R.id.textView);
        preferredImageWidth = PREFERRED_IMAGE_WIDTH;
    }

    public float getPreferredImageWidth() {
        return preferredImageWidth;
    }
}
