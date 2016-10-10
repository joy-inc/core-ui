package com.joy.ui.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(int position, View v, T t);
}
