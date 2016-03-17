package com.jpvander.githubjobs.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import com.jpvander.githubjobs.R;

public class RecyclerTextViewAndImageViewHolder extends RecyclerView.ViewHolder {

    private static final float PREFERRED_IMAGE_WIDTH = 140.0f;

    public final TextView textView;
    public final ImageView imageView;
    private final float preferredImageWidth;
    private final Context context;

    public RecyclerTextViewAndImageViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.image_text_item_image_view);
        this.textView = (TextView) view.findViewById(R.id.image_text_item_text_view);
        preferredImageWidth = PREFERRED_IMAGE_WIDTH;
        context = view.getContext();
    }

    public Context getContext() {
        return context;
    }

    public float getPreferredImageWidth() {
        return preferredImageWidth;
    }
}
