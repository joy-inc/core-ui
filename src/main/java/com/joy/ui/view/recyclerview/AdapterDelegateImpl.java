package com.joy.ui.view.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.adapter.ExRvViewHolder;
import com.joy.utils.LayoutInflater;

/**
 * Created by Daisw on 16/8/6.
 */

public abstract class AdapterDelegateImpl<T extends DisplayableItem, VH extends ExRvViewHolder<T>> implements AdapterDelegate<DisplayableItem, VH> {

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull DisplayableItem item) {
        holder.invalidateItemView(position, (T) item);
    }

    public final <T extends View> T inflateLayout(@Nullable ViewGroup root, @LayoutRes int layoutResId) {
        return LayoutInflater.inflate(root.getContext(), layoutResId, root, false);
    }
}
