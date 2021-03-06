package com.jpvander.githubjobs.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jpvander.githubjobs.R;

public class RecyclerTableHolder extends RecyclerView.ViewHolder {

    private final TableLayout tableLayout;
    private final int paddingPx;
    private final Context context;

    public RecyclerTableHolder(View view) {
        super(view);
        this.tableLayout = (TableLayout) view.findViewById(R.id.table_item_table_layout);
        float displayScale = view.getResources().getDisplayMetrics().density;
        this.paddingPx = (int) (view.getResources()
                .getDimension(R.dimen.small_margin) * displayScale);
        this.context = view.getContext();
    }

    public void addRowWithHtmlContent(String label, String content) {
        if (null == label) { label = ""; }
        if (null == content) { content = ""; }

        TableRow tableRow = new TableRow(context);
        tableRow.setPaddingRelative(0, paddingPx, 0, 0);

        TextView labelView = new TextView(context);
        labelView.setText(label);
        labelView.setPaddingRelative(0, 0, paddingPx, 0);

        TextView contentView = new TextView(context);
        contentView.setAutoLinkMask(Linkify.ALL);
        contentView.setLinksClickable(true);
        contentView.setText(Html.fromHtml(content));

        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        tableRow.setLayoutParams(rowParams);

        TableRow.LayoutParams labelParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        labelView.setLayoutParams(labelParams);

        TableRow.LayoutParams contentParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        contentView.setLayoutParams(contentParams);

        tableRow.addView(labelView);
        tableRow.addView(contentView);
        tableLayout.addView(tableRow);
    }
}
