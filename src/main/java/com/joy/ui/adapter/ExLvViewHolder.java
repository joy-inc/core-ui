package com.joy.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joy.ui.view.recyclerview.JRecyclerView;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;

import static android.support.v7.widget.ListViewCompat.NO_POSITION;

/**
 * Created by KEVIN.DAI on 15/7/16.
 *
 * @param <T>
 * @see {@link JRecyclerView,ExRvViewHolder}
 */
public abstract class ExLvViewHolder<T> {

    public final View itemView;
    int mPosition = NO_POSITION;

    public ExLvViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        this(LayoutInflater.inflate(parent.getContext(), layoutResId, parent, false));
    }

    public ExLvViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
    }

    public final View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return (T) getItemView().findViewById(id);
    }

    public final int getPosition() {
        return mPosition;
    }

    public final int getLayoutPosition() {
        return mPosition;
    }

    public final int getAdapterPosition() {
        return mPosition;
    }

    public abstract void invalidateItemView(int position, T t);

    protected final void showToast(String text) {
        ToastUtil.showToast(getItemView().getContext().getApplicationContext(), text);
    }

    protected final void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    protected final void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    protected final void showView(View v) {
        ViewUtil.showView(v);
    }

    protected final void hideView(View v) {
        ViewUtil.hideView(v);
    }

    protected final void goneView(View v) {
        ViewUtil.goneView(v);
    }

    protected final void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    protected final void showImageView(ImageView v, @NonNull Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    protected final void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    protected final void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    protected final String getString(@StringRes int resId) {
        return getItemView().getResources().getString(resId);
    }

    protected final String getString(@StringRes int resId, Object... formatArgs) {
        return getItemView().getResources().getString(resId, formatArgs);
    }

    protected final String[] getStringArray(@ArrayRes int resId) {
        return getItemView().getResources().getStringArray(resId);
    }
}
