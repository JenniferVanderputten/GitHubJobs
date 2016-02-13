package com.jpvander.githubjobs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

@SuppressWarnings("unused")
class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;
    private int listOrientation;
    private final Drawable divider;

    public DividerItemDecoration(Context context) {
        this.leftPadding = 0;
        this.rightPadding = 0;
        this.topPadding = 0;
        this.bottomPadding = 0;
        this.listOrientation = LinearLayout.VERTICAL;
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
        if (this.listOrientation == LinearLayout.VERTICAL) {
            drawDividerForVerticalList(canvas, parent);
        }
    }

    private void drawDividerForVerticalList(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int childPosition = 0; childPosition < childCount; childPosition++) {
            View child = parent.getChildAt(childPosition);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin + bottomPadding;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public int getListOrientation() {
        return listOrientation;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public void setListOrientation(int listOrientation) {
        this.listOrientation = listOrientation;
    }
}
