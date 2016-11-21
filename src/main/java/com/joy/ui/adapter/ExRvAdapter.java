package com.joy.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 *
 * @param <K>
 * @param <T>
 */
public abstract class ExRvAdapter<K extends ExRvViewHolder<T>, T> extends RecyclerView.Adapter<K> {

    private List<T> mData;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private int mHeadersCount;

    protected ExRvAdapter() {
        this(null);
    }

    protected ExRvAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        holder.invalidateItemView(position, getItem(position));
    }

    public T getItem(int position) {
        if (mData == null)
            return null;

        T t = null;
        try {
            t = mData.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    public void add(int position, T t) {
        if (mData != null && t != null)
            mData.add(position, t);
    }

    public void add(T t) {
        if (mData != null && t != null)
            mData.add(t);
    }

    public void addAll(List<T> ts) {
        if (ts == null)
            return;

        if (mData == null) {
            mData = ts;
        } else {
            mData.addAll(ts);
        }
    }

    public void addAll(int position, List<T> ts) {
        if (mData != null && ts != null)
            mData.addAll(position, ts);
    }

    public int indexOf(T t) {
        return mData == null ? -1 : mData.indexOf(t);
    }

    public void remove(T t) {
        if (mData != null)
            mData.remove(t);
    }

    public void remove(int position) {
        if (mData != null)
            mData.remove(position);
    }

    public void removeAll() {
        if (mData != null)
            mData.clear();
    }

    public void clear() {
        if (mData != null)
            mData.clear();
    }

    public boolean checkPosition(int position) {
        return position >= 0 && position < getItemCount();
    }

    /*
     * click listener part
	 */
    public void setOnItemClickListener(OnItemClickListener<T> lisn) {
        mOnItemClickListener = lisn;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> lisn) {
        mOnItemLongClickListener = lisn;
    }

    protected void callbackOnItemClickListener(int position, View view) {
        position -= mHeadersCount;
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(position, view, getItem(position));
    }

    protected void callbackOnItemLongClickListener(int position, View view) {
        position -= mHeadersCount;
        if (mOnItemLongClickListener != null)
            mOnItemLongClickListener.onItemLongClick(position, view, getItem(position));
    }

    public void setHeadersCount(int headersCount) {
        mHeadersCount = headersCount;
    }

    public int getHeadersCount() {
        return mHeadersCount;
    }
}
