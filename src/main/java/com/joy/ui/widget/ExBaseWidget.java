package com.joy.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * 布局的组件类，用于分离Activity中成块的布局
 *
 * @author yhb
 */
public abstract class ExBaseWidget {

    private Activity mActivity;
    private View mContentView;
    private OnWidgetViewClickListener mClickListener;

    protected ExBaseWidget(Activity activity) {
        mActivity = activity;
    }

    protected void setContentView(View v) {
        mContentView = v;
        initData();
        initTitleView();
        initContentView();
    }

    protected void initData() {
    }

    protected void initTitleView() {
    }

    protected void initContentView() {
    }

    protected boolean isActivityFinishing() {
        return mActivity == null || mActivity.isFinishing();
    }

    public Activity getActivity() {
        return mActivity;
    }

    public View getContentView() {
        return mContentView;
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

    public void setOnWidgetViewClickListener(OnWidgetViewClickListener lisn) {
        mClickListener = lisn;
    }

    protected void callbackWidgetViewClickListener(View v) {
        if (mClickListener != null) {
            mClickListener.onWidgetViewClick(v);
        }
    }

    public interface OnWidgetViewClickListener {
        void onWidgetViewClick(View v);
    }
}
