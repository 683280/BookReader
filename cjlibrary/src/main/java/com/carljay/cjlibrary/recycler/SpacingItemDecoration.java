package com.carljay.cjlibrary.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/12/24.
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = spacing;
        if (parent.getChildLayoutPosition(view) == 0)
            outRect.top = spacing;
    }
}
