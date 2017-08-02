package com.joy.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.joy.utils.CollectionUtil;
import com.joy.utils.LayoutInflater;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/11/10.
 *
 * @param <VH>
 * @param <T>
 */
public abstract class ExRvAdapter<VH extends ExRvViewHolder<T>, T> extends RecyclerView.Adapter<VH> {

    private List<T> mTs;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private int mHeadersCount;

    protected ExRvAdapter() {
    }

    protected ExRvAdapter(List<T> ts) {
        mTs = ts;
    }

    @Override
    public int getItemCount() {
        return mTs == null ? 0 : mTs.size();
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        vh.invalidateItemView(position, getItem(position));
    }

    public T getItem(int position) {
        if (mTs == null) {
            return null;
        }
        try {
            return mTs.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void setData(List<T> ts) {
        mTs = ts;
    }

    public List<T> getData() {
        return mTs;
    }

    public void add(int position, T t) {
        if (mTs != null && t != null) {
            mTs.add(position, t);
        }
    }

    public void add(T t) {
        if (mTs != null && t != null) {
            mTs.add(t);
        }
    }

    public void addAll(List<T> ts) {
        if (ts == null) {
            return;
        }
        if (mTs == null) {
            mTs = ts;
        } else {
            mTs.addAll(ts);
        }
    }

    public void addAll(int position, List<T> ts) {
        if (mTs != null && ts != null) {
            mTs.addAll(position, ts);
        }
    }

    public int indexOf(T t) {
        return mTs == null ? -1 : mTs.indexOf(t);
    }

    public void remove(T t) {
        if (mTs != null) {
            mTs.remove(t);
        }
    }

    public void remove(int position) {
        if (mTs != null) {
            mTs.remove(position);
        }
    }

    public void removeAll() {
        if (mTs != null) {
            mTs.clear();
        }
    }

    public void clear() {
        removeAll();
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
        T t = getItem(position);
        if (mOnItemClickListener != null && t != null) {
            mOnItemClickListener.onItemClick(position, view, t);
        }
    }

    protected void callbackOnItemLongClickListener(int position, View view) {
        position -= mHeadersCount;
        T t = getItem(position);
        if (mOnItemLongClickListener != null && t != null) {
            mOnItemLongClickListener.onItemLongClick(position, view, t);
        }
    }

    protected void callbackOnItemClickListener(VH vh, View... targetViews) {
        if (CollectionUtil.isEmpty(targetViews)) {
            targetViews = new View[]{vh.getItemView()};
        }
        for (View targetView : targetViews) {
            targetView.setOnClickListener((v) -> callbackOnItemClickListener(vh.getAdapterPosition(), v));
        }
    }

    protected void callbackOnItemLongClickListener(VH vh, View... targetViews) {
        if (CollectionUtil.isEmpty(targetViews)) {
            targetViews = new View[]{vh.getItemView()};
        }
        for (View targetView : targetViews) {
            targetView.setOnLongClickListener(v -> {
                callbackOnItemLongClickListener(vh.getAdapterPosition(), v);
                return true;
            });
        }
    }

    public void setHeadersCount(int headersCount) {
        mHeadersCount = headersCount;
    }

    public int getHeadersCount() {
        return mHeadersCount;
    }

    public final <T extends View> T inflateLayout(@Nullable ViewGroup root, @LayoutRes int layoutResId) {
        return LayoutInflater.inflate(root.getContext(), layoutResId, root, false);
    }
}
