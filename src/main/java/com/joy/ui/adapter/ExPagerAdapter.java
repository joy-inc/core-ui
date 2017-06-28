package com.joy.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daisw
 */
public abstract class ExPagerAdapter<T> extends PagerAdapter {

    private List<T> mTs;
    private SparseArray<View> mCacheViews;
    private OnItemClickListener<T> mOnItemClickListener;

    public ExPagerAdapter() {
        mTs = new ArrayList<>();
        mCacheViews = new SparseArray<>();
    }

    public ExPagerAdapter(@NonNull List<T> ts) {
        mTs = ts;
        mCacheViews = new SparseArray<>();
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public int getCount() {
        return mTs.size();
    }

    public T getItem(int position) {
        try {
            return mTs.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SparseArray<View> getCacheViews() {
        return mCacheViews;
    }

    public View getCacheView(int position) {
        return mCacheViews.get(position % 4);// ViewPager保留三张，另预留一张用于下一个item
    }

    public void setData(@NonNull List<T> ts) {
        this.mTs = ts;
    }

    public List<T> getData() {
        return mTs;
    }

    public void add(int position, T t) {
        if (t != null) {
            mTs.add(position, t);
        }
    }

    public void add(T t) {
        if (t != null) {
            mTs.add(t);
        }
    }

    public void addAll(List<T> ts) {
        if (ts == null) {
            return;
        }
        mTs.addAll(ts);
    }

    public void addAll(int position, List<T> ts) {
        if (ts != null) {
            mTs.addAll(position, ts);
        }
    }

    public int indexOf(T t) {
        return mTs.indexOf(t);
    }

    public void remove(T t) {
        mTs.remove(t);
    }

    public void remove(int position) {
        mTs.remove(position);
    }

    public void removeAll() {
        mTs.clear();
    }

    public void clear() {
        mTs.clear();
    }

    @Override
    public int getItemPosition(Object object) {
        if (isEmpty()) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getCacheView(position);
        if (view == null) {
            view = getItemView(container, position);
            mCacheViews.put(position, view);
        }
        if (view.getParent() != null) {
            container.removeView(view);
        }
        container.addView(view);
        invalidateItemView(position, getItem(position));
        return view;
    }

    protected abstract View getItemView(ViewGroup container, int position);

    protected abstract void invalidateItemView(int position, T t);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setOnItemClickListener(OnItemClickListener<T> lisn) {
        mOnItemClickListener = lisn;
    }

    public void callbackOnItemClickListener(int position, View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position, view, getItem(position));
        }
    }
}
