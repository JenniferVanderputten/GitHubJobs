package com.jpvander.githubjobs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jenva on 2/4/2016.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;
    private Drawable divider;

    public DividerItemDecoration(Context context, int leftPadding, int rightPadding, int topPadding,
                                 int bottomPadding, int listOrientation) {
        this.leftPadding = leftPadding;
        this.rightPadding = rightPadding;
        this.topPadding = topPadding;
        this.bottomPadding = bottomPadding;
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        divider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    @Override
    public void getItemOffsets(Rect outerRect, View view, RecyclerView parent,
                               RecyclerView.State recyclerState) {

        outerRect.left += leftPadding;
        outerRect.right += rightPadding;
        outerRect.top += topPadding;
        outerRect.bottom += bottomPadding;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State recyclerState) {
        //TODO: Add handling for horizontal list layouts
        drawDividerForVerticalList(canvas, parent);
    }
    public void drawDividerForVerticalList(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int childPosition = 0; childPosition < childCount - 1; childPosition++) {
            View child = parent.getChildAt(childPosition);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin + bottomPadding;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }
}
