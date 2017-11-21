package com.joy.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
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
import com.joy.ui.interfaces.BaseView;
import com.joy.ui.utils.SnackbarUtil;
import com.joy.utils.DensityUtil;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * 基本的UI框架
 * Created by KEVIN.DAI on 16/11/22.
 */
public abstract class BaseUiFragment extends RxFragment implements BaseView<FragmentEvent> {

    protected CharSequence mLabel;
    protected FrameLayout mContentParent;
    protected View mContentView;
    protected boolean isFront;

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentParent = new FrameLayout(getContext());
        return mContentParent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContentParent.removeAllViews();
        mContentParent = null;
        if (mContentView instanceof ViewGroup) {
            ((ViewGroup) mContentView).removeAllViews();
        }
        mContentView = null;
    }

    public void setContentView(@LayoutRes int layoutResId) {
        setContentView(inflateLayout(layoutResId, mContentParent, true));
    }

    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    public void setContentView(View contentView, LayoutParams params) {
        if (params != null) {
            contentView.setLayoutParams(params);
        }
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

    public FrameLayout getContentParent() {
        return mContentParent;
    }

    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContentParent.setBackground(background);
        } else {
            mContentParent.setBackgroundDrawable(background);
        }
    }

    public void setBackgroundResource(@DrawableRes int resId) {
        mContentParent.setBackgroundResource(resId);
    }

    public void setBackgroundColor(@ColorInt int color) {
        mContentParent.setBackgroundColor(color);
    }

    public View getContentView() {
        return mContentView;
    }

    public LayoutParams getContentViewLp() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    public <T extends BaseUiFragment> T setLabel(CharSequence label) {
        mLabel = label;
        return (T) this;
    }

    public <T extends BaseUiFragment> T setLabel(@StringRes int resId) {
        mLabel = BaseApplication.getAppString(resId);
        return (T) this;
    }

    public CharSequence getLabel() {
        return mLabel;
    }

    public void onVisible() {
        setFront(true);
    }

    public void onInvisible() {
        setFront(false);
    }

    @Override
    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public boolean isFinishing() {
        return getActivity() == null || getActivity().isFinishing();
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showToast(getContext(), text);
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    @Override
    @SuppressWarnings("ResourceType")
    public void showSnackbar(@NonNull CharSequence text) {
        showSnackbar(text, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR, textColor);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor) {
        if (textColor == SnackbarUtil.NO_COLOR) {
            textColor = getResources().getColor(R.color.color_text_primary);
        }
        SnackbarUtil.showSnackbar(getContentView(), text, duration, bgColor, textColor);
    }

    @Override
    public void showView(View v) {
        ViewUtil.showView(v);
    }

    @Override
    public void hideView(View v) {
        ViewUtil.hideView(v);
    }

    @Override
    public void goneView(View v) {
        ViewUtil.goneView(v);
    }

    @Override
    public void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    @Override
    public void showImageView(ImageView v, Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    @Override
    public void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    @Override
    public void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(getContext(), layoutResId);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(getContext(), layoutResId, root);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(getContext(), layoutResId, root, attachToRoot);
    }

    public int DP(float dp) {
        return DensityUtil.dip2px(getActivity().getApplicationContext(), dp);
    }

    public int getDimensionPixelSize(@DimenRes int dimensId) {
        return DensityUtil.getDimensionPixelSize(getActivity().getApplicationContext(), dimensId);
    }

    public int getColorInt(@ColorRes int colorResId) {
        return getResources().getColor(colorResId);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return mContentParent.findViewById(id);
    }
}
