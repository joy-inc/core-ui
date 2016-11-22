package com.joy.ui.fragment;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.joy.ui.BaseApplication;
import com.joy.ui.R;
import com.joy.ui.fragment.interfaces.BaseView;
import com.joy.ui.utils.DimenCons;
import com.joy.ui.utils.SnackbarUtil;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 基本的UI框架
 * Created by KEVIN.DAI on 16/11/22.
 */
public abstract class BaseUiFragment extends RxFragment implements BaseView, DimenCons {

    private CharSequence mLabel;
    private FrameLayout mContentParent;
    private View mContentView;

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentParent = new FrameLayout(getContext());
        return mContentParent;
    }

    public final void setContentView(@LayoutRes int layoutResId) {
        setContentView(inflateLayout(layoutResId, mContentParent, true));
    }

    public final void setContentView(View contentView) {
        setContentView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    public final void setContentView(View contentView, LayoutParams params) {
        contentView.setLayoutParams(params);
        mContentView = contentView;

        resolveThemeAttribute();

        wrapContentView(mContentParent, contentView);

        initData();
        initContentView();
    }

    public void resolveThemeAttribute() {
    }

    public void wrapContentView(FrameLayout contentParent, View contentView) {
        // add transition animation
//        LayoutTransition lt = new LayoutTransition();
//        lt.setDuration(100);
//        contentParent.setLayoutTransition(lt);

        if (contentParent != contentView) {
            contentParent.addView(contentView);
        }
    }

    protected void initData() {
    }

    protected void initContentView() {
    }

    public final FrameLayout getContentParent() {
        return mContentParent;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public final void setBackground(Drawable background) {
        mContentParent.setBackground(background);
    }

    public final void setBackgroundResource(@DrawableRes int resId) {
        mContentParent.setBackgroundResource(resId);
    }

    public final void setBackgroundColor(@ColorInt int color) {
        mContentParent.setBackgroundColor(color);
    }

    public final View getContentView() {
        return mContentView;
    }

    public final LayoutParams getContentViewLp() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    public final BaseUiFragment setLabel(CharSequence label) {
        mLabel = label;
        return this;
    }

    public final BaseUiFragment setLable(@StringRes int resId) {
        mLabel = BaseApplication.getAppString(resId);
        return this;
    }

    public final CharSequence getLabel() {
        return mLabel;
    }

    public void onVisible() {
    }

    @Override
    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public final boolean isFinishing() {
        return getActivity() == null || getActivity().isFinishing();
    }

    @Override
    public final void showToast(String text) {
        ToastUtil.showToast(getContext(), text);
    }

    @Override
    public final void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public final void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    @Override
    @SuppressWarnings("ResourceType")
    public final void showSnackbar(@NonNull CharSequence text) {
        showSnackbar(text, Snackbar.LENGTH_SHORT);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR, textColor);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor) {
        if (textColor == SnackbarUtil.NO_COLOR) {
            textColor = getResources().getColor(R.color.color_text_primary);
        }
        SnackbarUtil.showSnackbar(getContentView(), text, duration, bgColor, textColor);
    }

    @Override
    public final void showView(View v) {
        ViewUtil.showView(v);
    }

    @Override
    public final void hideView(View v) {
        ViewUtil.hideView(v);
    }

    @Override
    public final void goneView(View v) {
        ViewUtil.goneView(v);
    }

    @Override
    public final void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    @Override
    public final void showImageView(ImageView v, Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    @Override
    public final void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    @Override
    public final void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    @Override
    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(getContext(), layoutResId);
    }

    @Override
    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(getContext(), layoutResId, root);
    }

    @Override
    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(getContext(), layoutResId, root, attachToRoot);
    }
}
