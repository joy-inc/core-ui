package com.joy.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joy.utils.DensityUtil;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;

/**
 * Created by KEVIN.DAI on 15/11/10.
 * Modified by KEVIN.DAI on 16/7/6.(add some methods)
 * Modified by Daisw on 17/5/17.(add findViewById method)
 *
 * @param <T>
 */
public abstract class ExRvViewHolder<T> extends RecyclerView.ViewHolder {

    public ExRvViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        super(LayoutInflater.inflate(parent.getContext(), layoutResId, parent, false));
    }

    public ExRvViewHolder(View itemView) {
        super(itemView);
    }

    public final View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return (T) getItemView().findViewById(id);
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

    protected final int getDimenPixelSize(@DimenRes int dimenResId) {
        return getItemView().getResources().getDimensionPixelSize(dimenResId);
    }

    protected final int DP(float dp) {
        return DensityUtil.dip2px(getItemView().getContext().getApplicationContext(), dp);
    }
}
