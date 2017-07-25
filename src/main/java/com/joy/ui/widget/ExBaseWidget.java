package com.joy.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

import com.joy.utils.DensityUtil;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 布局的组件类，用于分离Activity中成块的布局
 *
 * @author yhb
 *         <p>
 *         Modified by Daisw on 2017/6/25.
 */
public abstract class ExBaseWidget {

    private Activity mActivity;
    private View mContentView;
    private OnViewClickListener mOnViewClickListener;

    protected ExBaseWidget(Activity activity) {
        mActivity = activity;
    }

    protected final void setContentView(@LayoutRes int layoutResId) {
        setContentView(inflateLayout(layoutResId));
    }

    protected final void setContentView(@LayoutRes int layoutResId, ViewGroup.LayoutParams params) {
        setContentView(inflateLayout(layoutResId), params);
    }

    protected final void setContentView(View contentView) {
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        }
        setContentView(contentView, lp);
    }

    protected final void setContentView(View contentView, ViewGroup.LayoutParams params) {
        contentView.setLayoutParams(params);
        mContentView = contentView;

        wrapContentView(contentView);

        initData();
        initTitleView();
        initContentView();
    }

    protected void wrapContentView(View contentView) {
        // add transition animation
//        LayoutTransition lt = new LayoutTransition();
//        lt.setDuration(100);
//        contentParent.setLayoutTransition(lt);
    }

    protected void initData() {
    }

    protected void initTitleView() {
    }

    protected void initContentView() {
    }

    protected final boolean isFinishing() {
        return mActivity == null || mActivity.isFinishing();
    }

    public final Activity getActivity() {
        return mActivity;
    }

    public View getContentView() {
        return mContentView;
    }

    public final LayoutParams getContentViewLp() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        mOnViewClickListener = listener;
    }

    protected void callbackOnViewClickListener(View v) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(v);
        }
    }

    public interface OnViewClickListener {
        void onViewClick(View v);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return (T) getContentView().findViewById(id);
    }

    public final void showToast(String text) {
        ToastUtil.showToast(getActivity().getApplicationContext(), text);
    }

    public final void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    public final void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    public final String getString(@StringRes int resId) {
        return getActivity().getResources().getString(resId);
    }

    public final String getString(@StringRes int resId, Object... formatArgs) {
        return getActivity().getResources().getString(resId, formatArgs);
    }

    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(getActivity(), layoutResId);
    }

    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(getActivity(), layoutResId, root);
    }

    public final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(getActivity(), layoutResId, root, attachToRoot);
    }

    public final int DP(float dp) {
        return DensityUtil.dip2px(getActivity().getApplicationContext(), dp);
    }

    public final int getDimensionPixelSize(@DimenRes int dimensId) {
        return DensityUtil.getDimensionPixelSize(getActivity().getApplicationContext(), dimensId);
    }

    public final int getColorInt(@ColorRes int colorResId) {
        return getActivity().getResources().getColor(colorResId);
    }
}
