package com.joy.ui.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by KEVIN.DAI on 15/11/20.
 */
public class JListView extends ListView implements OnScrollListener {

    private OnLoadMoreListener mOnLoadMoreListener;
    private JFooterView mFooterView;
    private boolean mIsLoadMoreEnable = true;
    private boolean mIsLoadingMore;

    public JListView(Context context) {
        super(context);
        init(context);
    }

    public JListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOnScrollListener(this);
        mFooterView = new JFooterView(context);
        mFooterView.setOnRetryListener(() -> startLoadMore(false));
    }

    public void addLoadMoreIfNotExist() {
        if (getFooterViewsCount() == 0) {
            addFooterView(mFooterView);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int extraItemsCount = getHeaderViewsCount() + getFooterViewsCount();
        if (!mIsLoadMoreEnable || mIsLoadingMore || isLoadMoreFailed() || totalItemCount <= extraItemsCount) {
            return;
        }
        if (visibleItemCount + firstVisibleItem == totalItemCount) {
            startLoadMore(true);
        }
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

    public void setLoadMoreListener(OnLoadMoreListener l) {
        mOnLoadMoreListener = l;
    }
}
