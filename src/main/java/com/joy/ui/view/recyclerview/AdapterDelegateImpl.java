package com.joy.ui.view.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.adapter.ExRvViewHolder;

/**
 * Created by Daisw on 16/8/6.
 */

public abstract class AdapterDelegateImpl<T extends DisplayableItem, VH extends ExRvViewHolder<T>> implements AdapterDelegate<DisplayableItem, VH> {

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull DisplayableItem item) {

        holder.invalidateItemView(position, (T) item);
    }

    public View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {

        return LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
    }
}
