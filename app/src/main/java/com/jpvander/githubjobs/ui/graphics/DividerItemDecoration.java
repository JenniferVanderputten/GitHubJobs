package com.jpvander.githubjobs.ui.graphics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jpvander.githubjobs.R;

@SuppressWarnings("unused")
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int paddingPx;
    private final TypedArray styledAttributes;

    public DividerItemDecoration(Context context) {
        float displayScale = context.getResources().getDisplayMetrics().density;
        float margin = context.getResources().getDimension(R.dimen.small_margin);
        paddingPx = (int) (margin * displayScale);
        int[] attributes = new int[]{android.R.attr.listDivider};
        styledAttributes = context.obtainStyledAttributes(attributes);

    }

    @Override
    public void getItemOffsets(Rect outerRect, View view, RecyclerView parent,
                               RecyclerView.State recyclerState) {
        outerRect.top += paddingPx;
        outerRect.bottom += paddingPx;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State recyclerState) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        Drawable divider = styledAttributes.getDrawable(0);

        int childCount = parent.getChildCount();
        for (int childPosition = 0; childPosition < childCount; childPosition++) {
            if (null != divider) {
                View child = parent.getChildAt(childPosition);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin + paddingPx;
                int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
        }
    }
}
