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

    private List<T> mData;
    private SparseArray<View> mViews;
    private OnItemClickListener<T> mOnItemClickListener;

    public ExPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new SparseArray<>();
    }

    public ExPagerAdapter(@NonNull List<T> data) {
        mData = data;
        mViews = new SparseArray<>();
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public T getItem(int position) {
        T item = null;
        try {
            item = mData.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public SparseArray<View> getViews() {
        return mViews;
    }

    public View getItemView(int position) {
        return mViews.get(position);
    }

    public void setData(@NonNull List<T> data) {
        this.mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    public void add(int position, T t) {
        if (t != null) {
            mData.add(position, t);
        }
    }

    public void add(T t) {
        if (t != null) {
            mData.add(t);
        }
    }

    public void addAll(List<T> ts) {
        if (ts == null) {
            return;
        }
        mData.addAll(ts);
    }

    public void addAll(int position, List<T> ts) {
        if (ts != null) {
            mData.addAll(position, ts);
        }
    }

    public int indexOf(T t) {
        return mData.indexOf(t);
    }

    public void remove(T t) {
        mData.remove(t);
    }

    public void remove(int position) {
        mData.remove(position);
    }

    public void removeAll() {
        mData.clear();
    }

    public void clear() {
        mData.clear();
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
        View view = mViews.get(position);
        if (view == null) {
            view = getView(position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    protected abstract View getView(int position);

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
