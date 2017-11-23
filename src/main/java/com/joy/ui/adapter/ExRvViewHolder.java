package com.joy.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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

    public final void showToast(String text) {
        ToastUtil.showToast(getItemView().getContext().getApplicationContext(), text);
    }

    public final void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    public final void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    public final void showView(View v) {
        ViewUtil.showView(v);
    }

    public final void hideView(View v) {
        ViewUtil.hideView(v);
    }

    public final void goneView(View v) {
        ViewUtil.goneView(v);
    }

    public final void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    public final void showImageView(ImageView v, @NonNull Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    public final void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    public final void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    public final String getString(@StringRes int resId) {
        return getItemView().getResources().getString(resId);
    }

    public final String getString(@StringRes int resId, Object... formatArgs) {
        return getItemView().getResources().getString(resId, formatArgs);
    }

    public final String[] getStringArray(@ArrayRes int resId) {
        return getItemView().getResources().getStringArray(resId);
    }

    @ColorInt
    public final int getColor(@ColorRes int colorResId) {
        return getItemView().getContext().getResources().getColor(colorResId);
    }
}
