package com.joy.ui.fragment.interfaces;

import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.joy.ui.RefreshMode;
import com.joy.ui.adapter.ExRvAdapter;

/**
 * Created by Daisw on 16/6/7.
 */
public interface BaseViewNetRv extends BaseViewNet {

    enum Theme {LIGHT, DARK}

    RecyclerView provideRecyclerView();
    LayoutManager provideLayoutManager();
    RecyclerView getRecyclerView();
    LayoutManager getLayoutManager();
    void setAdapter(ExRvAdapter adapter);
    ExRvAdapter getAdapter();

    int getHeaderViewsCount();
    int getFooterViewsCount();
    void addHeaderView(View v);
    void addFooterView(View v);
    void removeHeaderView(View v);
    void removeFooterView(View v);

    SwipeRefreshLayout getSwipeRefreshLayout();
    void setSwipeRefreshEnable(boolean enable);
    boolean isSwipeRefreshing();
    void showSwipeRefresh();
    void hideSwipeRefresh();
    void setSwipeRefreshColors(@ColorRes int... resIds);

    void setLoadMoreEnable(boolean enable);
    boolean isLoadMoreEnable();
    boolean isLoadingMore();
    void stopLoadMore();
    void setLoadMoreFailed();
    void hideLoadMore();
    void setLoadMoreTheme(Theme theme);
    void setLoadMoreColor(@ColorRes int resId);

    void setRefreshMode(RefreshMode mode);
}
