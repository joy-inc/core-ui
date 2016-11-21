package com.joy.ui.view.recyclerview;

import android.view.ViewGroup;

import com.joy.ui.adapter.ExRvAdapter;
import com.joy.ui.adapter.ExRvViewHolder;

import java.util.List;

/**
 * Created by Daisw on 16/8/5.
 */

public class AdapterDelegateCombine<VH extends ExRvViewHolder<DisplayableItem>> extends ExRvAdapter<VH, DisplayableItem> {

    private AdapterDelegateManager<DisplayableItem, VH> delegateManager;

    public AdapterDelegateCombine(AdapterDelegateManager<DisplayableItem, VH> delegateManager) {
        this(null, delegateManager);
    }

    public AdapterDelegateCombine(List<DisplayableItem> data, AdapterDelegateManager<DisplayableItem, VH> delegateManager) {
        super(data);
        this.delegateManager = delegateManager;
    }

    @Override
    public int getItemViewType(int position) {
        return delegateManager.getItemViewType(position, getItem(position));
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegateManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        delegateManager.onBindViewHolder(holder, position, getItem(position));
    }
}
