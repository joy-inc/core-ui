package com.joy.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.joy.ui.view.recyclerview.JRecyclerView;
import com.joy.utils.CollectionUtil;
import com.joy.utils.LayoutInflater;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/16.
 *
 * @param <VH>
 * @param <T>
 * @see {@link JRecyclerView,ExRvAdapter}
 */
public abstract class ExLvAdapter<VH extends ExLvViewHolder<T>, T> extends BaseAdapter {

    protected List<T> mTs;
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClickListener<T> mOnItemLongClickListener;

    protected ExLvAdapter() {
    }

    protected ExLvAdapter(List<T> ts) {
        mTs = ts;
    }

    @Override
    public int getCount() {
        return mTs == null ? 0 : mTs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh;
        if (convertView == null) {
            vh = onCreateViewHolder(parent, getItemViewType(position));
            convertView = vh.getItemView();
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        vh.mPosition = position;
        vh.invalidateItemView(position, getItem(position));
        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

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
        return position >= 0 && position < getCount();
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

    /**
     * @param position
     * @param view
     * @hide Use {@link #bindOnClickListener(ExLvViewHolder, View...)} instead.
     */
    protected void callbackOnItemClickListener(int position, View view) {
        T t = getItem(position);
        if (mOnItemClickListener != null && t != null) {
            mOnItemClickListener.onItemClick(position, view, t);
        }
    }

    /**
     * @param position
     * @param view
     * @hide Use {@link #bindOnLongClickListener(ExLvViewHolder, View...)} instead.
     */
    protected void callbackOnItemLongClickListener(int position, View view) {
        T t = getItem(position);
        if (mOnItemLongClickListener != null && t != null) {
            mOnItemLongClickListener.onItemLongClick(position, view, t);
        }
    }

    protected void bindOnClickListener(VH vh, View... targetViews) {
        if (CollectionUtil.isEmpty(targetViews)) {
            targetViews = new View[]{vh.getItemView()};
        }
        for (View targetView : targetViews) {
            targetView.setOnClickListener((v) -> callbackOnItemClickListener(vh.getAdapterPosition(), v));
        }
    }

    protected void bindOnLongClickListener(VH vh, View... targetViews) {
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

    public final <T extends View> T inflateLayout(@Nullable ViewGroup root, @LayoutRes int layoutResId) {
        return LayoutInflater.inflate(root.getContext(), layoutResId, root, false);
    }
}
