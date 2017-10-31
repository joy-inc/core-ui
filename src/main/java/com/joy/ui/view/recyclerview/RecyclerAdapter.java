package com.joy.ui.view.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.adapter.ExRvAdapter;

/**
 * Created by KEVIN.DAI on 15/12/1.
 */
public class RecyclerAdapter extends Adapter<ViewHolder> {

    // the real adapter for RecyclerView
    private Adapter<ViewHolder> mRealAdapter;
    private LayoutManager mLayoutManager;
    private SparseArray<View> mHeaderArrays, mFooterArrays;

    public RecyclerAdapter(Adapter<ViewHolder> adapter, LayoutManager lm) {
        mHeaderArrays = new SparseArray<>();
        mFooterArrays = new SparseArray<>();

        setWrappedAdapter(adapter);
        mLayoutManager = lm;

        if (lm instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) lm;
            glm.setSpanSizeLookup(new SpanSizeLookup(this, glm.getSpanCount()));
        }
    }

    private void setWrappedAdapter(Adapter<ViewHolder> adapter) {
        if (adapter == null) {
            return;
        }
        if (mRealAdapter != null) {
            notifyItemRangeRemoved(getHeadersCount(), getWrappedItemCount());
            mRealAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        mRealAdapter = adapter;
        mRealAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeadersCount(), getWrappedItemCount());
    }

    public Adapter<ViewHolder> getWrappedAdapter() {
        return mRealAdapter;
    }

    private int getWrappedItemCount() {
        return mRealAdapter.getItemCount();
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getWrappedItemCount();
    }

    @Override
    public long getItemId(int position) {
        if (isItem(position)) {
            return mRealAdapter.getItemId(position - getHeadersCount());
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderArrays.keyAt(position);
        } else if (isFooter(position)) {
            return mFooterArrays.keyAt(position - getHeadersCount() - getWrappedItemCount());
        } else {
            return mRealAdapter.getItemViewType(position - getHeadersCount());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderArrays.indexOfKey(viewType) >= 0) {
            return new RvViewHolder(mHeaderArrays.get(viewType));
        } else if (mFooterArrays.indexOfKey(viewType) >= 0) {
            return new RvViewHolder(mFooterArrays.get(viewType));
        } else {
            return mRealAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (isItem(position)) {
            mRealAdapter.onBindViewHolder(holder, position - getHeadersCount());
        } else {
            if (mLayoutManager != null && mLayoutManager instanceof StaggeredGridLayoutManager) {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.setFullSpan(true);
                holder.itemView.setLayoutParams(params);
            }
        }
    }

    private class RvViewHolder extends ViewHolder {
        RvViewHolder(View itemView) {
            super(itemView);
        }
    }

    public int getHeadersCount() {
        return mHeaderArrays.size();
    }

    public View getHeaderView(int index) {
        return mHeaderArrays.valueAt(index);
    }

    public int getFootersCount() {
        return mFooterArrays.size();
    }

    public View getFooterView(int index) {
        return mFooterArrays.valueAt(index);
    }

    private void syncRealAdapterHeadersCount() {
        if (mRealAdapter instanceof ExRvAdapter) {
            ((ExRvAdapter) mRealAdapter).setHeadersCount(getHeadersCount());
        }
    }

    public void addHeaderView(View v) {
        if (v == null) {
            return;
        }
        if (mHeaderArrays.size() == 0) {
            mHeaderArrays.put(Integer.MIN_VALUE, v);
            notifyItemInserted(getHeadersCount() - 1);
        } else if (mHeaderArrays.indexOfValue(v) < 0) {
            mHeaderArrays.put(mHeaderArrays.keyAt(mHeaderArrays.size() - 1) + 1, v);
            notifyItemInserted(getHeadersCount() - 1);
        }
        syncRealAdapterHeadersCount();
    }

    public void addFooterView(View v) {
        if (v == null) {
            return;
        }
        if (mFooterArrays.size() == 0) {
            mFooterArrays.put(Integer.MAX_VALUE, v);
            notifyItemInserted(getItemCount() - 1);
        } else if (mFooterArrays.indexOfValue(v) < 0) {
            mFooterArrays.put(mFooterArrays.keyAt(mFooterArrays.size() - 1) - 1, v);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public boolean removeHeader(View v) {
        int index = mHeaderArrays.indexOfValue(v);
        if (index >= 0) {
            mHeaderArrays.removeAt(index);
            notifyItemRemoved(index);
            syncRealAdapterHeadersCount();
            return true;
        }
        return false;
    }

    public void removeAllHeaders() {
        int size;
        if (mHeaderArrays != null && (size = mHeaderArrays.size()) > 0) {
            for (int i = 0; i < size; i++) {
                mHeaderArrays.removeAt(i);
            }
            notifyItemRangeRemoved(0, size);
            syncRealAdapterHeadersCount();
        }
    }

    public boolean removeFooter(View v) {
        int index = mFooterArrays.indexOfValue(v);
        if (index >= 0) {
            mFooterArrays.removeAt(index);
            notifyItemRemoved(getHeadersCount() + getWrappedItemCount() + index);
            return true;
        }
        return false;
    }

    public void removeAllFooters() {
        int size;
        if (mFooterArrays != null && (size = mFooterArrays.size()) > 0) {
            for (int i = 0; i < size; i++) {
                mFooterArrays.removeAt(i);
            }
            int startIndex = getHeadersCount() + getWrappedItemCount();
            notifyItemRangeRemoved(startIndex, startIndex + size);
        }
    }

    boolean isHeader(int position) {
        return getHeadersCount() > 0 && position < getHeadersCount();
    }

    private boolean isItem(int position) {
        return position >= getHeadersCount() && position < getHeadersCount() + getWrappedItemCount();
    }

    boolean isFooter(int position) {
        return getFootersCount() > 0 && position >= getHeadersCount() + getWrappedItemCount();
    }

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition + getHeadersCount(), toPosition + getHeadersCount(), itemCount);
        }
    };
}
