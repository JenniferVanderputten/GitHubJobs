package com.jpvander.githubjobs.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jpvander.githubjobs.R;

public class RecyclerTextViewAndImageButtonHolder extends RecyclerView.ViewHolder {

    public final TextView textView;
    public final ImageButton imageButton;

    public RecyclerTextViewAndImageButtonHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.text_item_text_view);
        this.imageButton = (ImageButton) view.findViewById(R.id.text_item_delete_button);
    }
}
