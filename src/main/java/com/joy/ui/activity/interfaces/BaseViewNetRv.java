package com.joy.ui.activity.interfaces;

import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.joy.ui.RefreshMode;
import com.joy.ui.adapter.ExRvAdapter;
import com.joy.ui.view.LoadMore;

/**
 * Created by Daisw on 16/6/7.
 */
public interface BaseViewNetRv extends BaseViewNet {

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
    void setOnRefreshListener(OnRefreshListener listener);
    void showSwipeRefresh();
    void hideSwipeRefresh();
    void setSwipeRefreshColors(@ColorRes int... resIds);

    void setLoadMoreEnable(boolean enable);
    boolean isLoadMoreEnable();
    void addLoadMoreIfNecessary();
    boolean isLoadingMore();
    void setOnLoadMoreListener(LoadMore.OnLoadMoreListener listener);
    void stopLoadMore();
    void setLoadMoreFailed();
    void hideLoadMore();
    void setLoadMoreTheme(LoadMore.Theme theme);
    void setLoadMoreHintColor(@ColorRes int resId);

    void setRefreshMode(RefreshMode mode);
}
