package com.example.supersub.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/*
 * Borrowed from stack overflow.
 * This class is simply an ItemDecoration primarily for RecyclerView(s) using vertically orientated LinearLayoutManager(s) that allow vertical spacing between the RecyclerView's items.
 * I will not comment this since I didn't write it.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
