package com.joy.ui.view.recyclerview;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.joy.ui.R;
import com.joy.ui.view.JFooterView;
import com.joy.ui.view.LoadMore;
import com.joy.utils.LayoutInflater;

/**
 * Created by KEVIN.DAI on 15/12/3.
 */
public class JRecyclerView extends RecyclerView {

    private LoadMore.OnLoadMoreListener mOnLoadMoreListener;
    private JFooterView mFooterView;
    private boolean mIsLoadMoreEnable = true;
    private boolean mIsLoadingMore;
    private int[] mPositions;
    private LayoutManager mLayoutMgr;

    public JRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public JRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        addOnScrollListener(new RvOnScrollListener());
        mFooterView = LayoutInflater.inflate(context, R.layout.lib_view_footer);
        mFooterView.setOnRetryListener(() -> startLoadMore(false));
    }

    public void addLoadMoreIfNotExist() {
        if (getAdapter() instanceof RecyclerAdapter) {
            RecyclerAdapter ra = (RecyclerAdapter) getAdapter();
            if (ra.getFootersCount() == 0) {
                ra.addFooterView(mFooterView);
            }
        }
    }

    public int getHeaderViewsCount() {
        if (getAdapter() instanceof RecyclerAdapter) {
            return ((RecyclerAdapter) getAdapter()).getHeadersCount();
        }
        return 0;
    }

    public int getFooterViewsCount() {
        if (getAdapter() instanceof RecyclerAdapter) {
            return ((RecyclerAdapter) getAdapter()).getFootersCount();
        }
        return 0;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) layout;
            mPositions = new int[sglm.getSpanCount()];
        }
        mLayoutMgr = layout;
    }

    private class RvOnScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int totalItemsCount = mLayoutMgr.getItemCount();
            int extraItemsCount = getHeaderViewsCount() + getFooterViewsCount();
            if (!mIsLoadMoreEnable || mIsLoadingMore || isLoadMoreFailed() || totalItemsCount <= extraItemsCount) {
                return;
            }
            int lastVisiblePos;
            if (mLayoutMgr instanceof LinearLayoutManager) {
                lastVisiblePos = ((LinearLayoutManager) mLayoutMgr).findLastVisibleItemPosition();
            } else if (mLayoutMgr instanceof GridLayoutManager) {
                lastVisiblePos = ((GridLayoutManager) mLayoutMgr).findLastVisibleItemPosition();
            } else if (mLayoutMgr instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) mLayoutMgr;
                sglm.findLastVisibleItemPositions(mPositions);
                lastVisiblePos = getLastPos(mPositions);
            } else {
                return;
            }

            if (lastVisiblePos == totalItemsCount - 1) {
                startLoadMore(true);
            }
        }
    }

    private int getLastPos(int[] positions) {
        int last = positions[0];
        for (int value : positions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }

    private void startLoadMore(boolean isAuto) {
        if (mIsLoadingMore) {
            return;
        }
        mIsLoadingMore = true;

//        mFooterView.ready();
        mFooterView.loading();

        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onRefresh(isAuto);
        }
    }

    public void stopLoadMore() {
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
//            mFooterView.done();
        }
    }

    public void setLoadMoreFailed() {
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
        }
        mFooterView.failed();
    }

    public boolean isLoadingMore() {
        return mIsLoadingMore;
    }

    public boolean isLoadMoreEnable() {
        return mIsLoadMoreEnable;
    }

    public boolean isLoadMoreFailed() {
        return mFooterView != null && mFooterView.isFailed();
    }

    public void setLoadMoreEnable(boolean enable) {
        if (mIsLoadMoreEnable == enable) {
            return;
        }
        mIsLoadMoreEnable = enable;

        if (enable) {
            mFooterView.ready();
        } else {
            mFooterView.done();
            if (mIsLoadingMore) {
                mIsLoadingMore = false;
            }
        }
    }

    public void hideLoadMore() {
//        mFooterView.done();
        setLoadMoreEnable(false);
    }

    public void setLoadMoreView(View v) {
        setLoadMoreView(v, (FrameLayout.LayoutParams) v.getLayoutParams());
    }

    public void setLoadMoreView(View v, FrameLayout.LayoutParams fllp) {
        mFooterView.setLoadingView(v, fllp);
    }

    public void setLoadMoreDarkTheme() {
        mFooterView.setDarkTheme();
    }

    public void setLoadMoreLightTheme() {
        mFooterView.setLightTheme();
    }

    public void setLoadMoreHintTextColor(@ColorRes int resId) {
        mFooterView.setHintTextColor(resId);
    }

    public void setOnLoadMoreListener(LoadMore.OnLoadMoreListener l) {
        mOnLoadMoreListener = l;
    }
}
