package com.joy.ui.view.recyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by KEVIN.DAI on 15/12/1.
 */
public class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private RecyclerAdapter mAdapter;
    private int mSpanSize;

    public SpanSizeLookup(RecyclerAdapter adapter, int spanSize) {
        mAdapter = adapter;
        mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}