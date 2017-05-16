package com.joy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.joy.ui.R;
import com.joy.ui.RefreshMode;
import com.joy.ui.adapter.ExRvAdapter;
import com.joy.ui.fragment.interfaces.BaseViewNetRv;
import com.joy.ui.view.JLoadingView;
import com.joy.ui.view.LoadMore;
import com.joy.ui.view.recyclerview.JRecyclerView;
import com.joy.ui.view.recyclerview.RecyclerAdapter;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Daisw on 16/6/7.
 */
public abstract class BaseHttpRvFragment extends BaseHttpUiFragment implements BaseViewNetRv {

    private SwipeRefreshLayout mSwipeRl;
    private RecyclerView mRecyclerView;
    private RefreshMode mRefreshMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView = provideRecyclerView();
        mRecyclerView.setLayoutManager(provideLayoutManager());
        setContentView(wrapSwipeRefresh(mRecyclerView));
        return v;
    }

    /**
     * 子类可以复写此方法，为自己定制RecyclerView
     *
     * @return 自定义的RecyclerView
     */
    @Override
    public RecyclerView provideRecyclerView() {
        JRecyclerView jrv = inflateLayout(R.layout.lib_view_recycler);
        jrv.setLoadMoreView(JLoadingView.getLoadMore(getActivity()));
        return jrv;
    }

    /**
     * 子类可以复写此方法，为自己定制LayoutManager，默认为LinearLayoutManager
     * LinearLayoutManager (线性显示，类似于ListView)
     * GridLayoutManager (线性宫格显示，类似于GridView)
     * StaggeredGridLayoutManager(线性宫格显示，类似于瀑布流)
     *
     * @return 自定义的LayoutManager
     */
    @Override
    public LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    private View wrapSwipeRefresh(View contentView) {
        mSwipeRl = new SwipeRefreshLayout(getActivity());
        mSwipeRl.setColorSchemeResources(R.color.color_accent);
        mSwipeRl.addView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        return mSwipeRl;
    }

    @Override
    public final RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public final LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    @Override
    public final void setRefreshMode(RefreshMode mode) {
        mRefreshMode = mode;
    }

    @Override
    public final int getHeaderViewsCount() {
        return ((RecyclerAdapter) mRecyclerView.getAdapter()).getHeadersCount();
    }

    @Override
    public final int getFooterViewsCount() {
        return ((RecyclerAdapter) mRecyclerView.getAdapter()).getFootersCount();
    }

    @Override
    public final void addHeaderView(View v) {
        Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null)
            throw new IllegalStateException(
                    "Cannot add header view to recycler -- setAdapter has not been called.");
        ((RecyclerAdapter) adapter).addHeaderView(v);
    }

    @Override
    public final void addFooterView(View v) {
        Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null)
            throw new IllegalStateException(
                    "Cannot add footer view to recycler -- setAdapter has not been called.");
        ((RecyclerAdapter) adapter).addFooterView(v);
    }

    @Override
    public final void removeHeaderView(View v) {
        ((RecyclerAdapter) mRecyclerView.getAdapter()).removeHeader(v);
    }

    @Override
    public final void removeFooterView(View v) {
        ((RecyclerAdapter) mRecyclerView.getAdapter()).removeFooter(v);
    }

    @Override
    public final void setAdapter(ExRvAdapter adapter) {
        mRecyclerView.setAdapter(new RecyclerAdapter(adapter, getLayoutManager()));
    }

    @Override
    public final ExRvAdapter getAdapter() {
        Adapter adapter = mRecyclerView.getAdapter();
        if (adapter instanceof RecyclerAdapter) {
            return (ExRvAdapter) ((RecyclerAdapter) adapter).getWrappedAdapter();
        } else {
            return (ExRvAdapter) adapter;
        }
    }

    @Override
    public final void showLoading() {
        switch (mRefreshMode) {
            case SWIPE:
                showSwipeRefresh();
                stopLoadMore();
                super.hideLoading();
                break;
            case FRAME:
                hideSwipeRefresh();
                hideLoadMore();
                super.showLoading();
                break;
            case LOADMORE:
                hideSwipeRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    public final void hideLoading() {
        switch (mRefreshMode) {
            case SWIPE:
                hideSwipeRefresh();
                break;
            case FRAME:
                super.hideLoading();
                break;
            case LOADMORE:
                stopLoadMore();
                break;
            default:
                break;
        }
    }

    @Override
    public final void showErrorTip() {
        switch (mRefreshMode) {
            case SWIPE:
                showToast(R.string.toast_common_timeout);
                break;
            case FRAME:
                if (getAdapter().getItemCount() == 0) {
                    super.showErrorTip();
                }
                break;
            case LOADMORE:
                setLoadMoreFailed();
                break;
            default:
                break;
        }
    }

    @Override
    public final void showEmptyTip() {
        if ((mRefreshMode == RefreshMode.SWIPE || mRefreshMode == RefreshMode.FRAME) && getAdapter().getItemCount() == 0) {
            super.showEmptyTip();
        }
    }

    @Override
    public final void hideContent() {
        if ((mRefreshMode == RefreshMode.SWIPE || mRefreshMode == RefreshMode.FRAME) && getAdapter().getItemCount() == 0) {
            super.hideContent();
        }
    }


    // swipe refresh
    // =============================================================================================
    @Override
    public final SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRl;
    }

    @Override
    public final void setSwipeRefreshEnable(boolean enable) {
        mSwipeRl.setEnabled(enable);
    }

    @Override
    public final boolean isSwipeRefreshing() {
        return mSwipeRl.isRefreshing();
    }

    @Override
    public final void setOnRefreshListener(OnRefreshListener listener) {
        mSwipeRl.setOnRefreshListener(listener);
    }

    @Override
    public final void showSwipeRefresh() {
        if (!isSwipeRefreshing()) {
            mSwipeRl.setRefreshing(true);
        }
    }

    @Override
    public final void hideSwipeRefresh() {
        if (isSwipeRefreshing()) {
            mSwipeRl.setRefreshing(false);
        }
    }

    @Override
    public final void setSwipeRefreshColors(@ColorRes int... resIds) {
        mSwipeRl.setColorSchemeResources(resIds);
    }
    // =============================================================================================


    // load more
    // =============================================================================================
    @Override
    public final void setLoadMoreEnable(boolean enable) {
        if (mRecyclerView instanceof JRecyclerView) {
            ((JRecyclerView) mRecyclerView).setLoadMoreEnable(enable);
        }
    }

    @Override
    public final boolean isLoadMoreEnable() {
        return mRecyclerView instanceof JRecyclerView && ((JRecyclerView) mRecyclerView).isLoadMoreEnable();
    }

    @Override
    public final void addLoadMoreIfNecessary() {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).addLoadMoreIfNotExist();
        }
    }

    @Override
    public final boolean isLoadingMore() {
        return isLoadMoreEnable() && ((JRecyclerView) mRecyclerView).isLoadingMore();
    }

    @Override
    public final void setOnLoadMoreListener(LoadMore.OnLoadMoreListener listener) {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).setOnLoadMoreListener(listener);
        }
    }

    @Override
    public final void stopLoadMore() {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).stopLoadMore();
        }
    }

    @Override
    public final void setLoadMoreFailed() {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).setLoadMoreFailed();
        }
    }

    @Override
    public final void hideLoadMore() {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).hideLoadMore();
        }
    }

    @Override
    public final void setLoadMoreTheme(LoadMore.Theme theme) {
        if (isLoadMoreEnable()) {
            switch (theme) {
                case LIGHT:
                    ((JRecyclerView) mRecyclerView).setLoadMoreLightTheme();
                    break;
                case DARK:
                    ((JRecyclerView) mRecyclerView).setLoadMoreDarkTheme();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public final void setLoadMoreHintColor(@ColorRes int resId) {
        if (isLoadMoreEnable()) {
            ((JRecyclerView) mRecyclerView).setLoadMoreHintTextColor(resId);
        }
    }
    // =============================================================================================
}
