package com.joy.ui.adapter;

import android.view.View;

public interface OnItemLongClickListener<T> {

    void onItemLongClick(int position, View v, T t);
}
