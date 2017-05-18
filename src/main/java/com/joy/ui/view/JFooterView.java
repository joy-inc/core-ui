package com.joy.ui.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.joy.ui.R;
import com.joy.utils.ViewUtil;

/**
 * Created by KEVIN.DAI on 15/11/20.
 */
public class JFooterView extends FrameLayout {

    private View mFlRoot;
    private View mLoadingDiv, mLoadingView;
    private OnRetryListener mOnRetryLisn;
    private JTextView mJtvHint, mJtvRetry;

    public JFooterView(Context context) {
        super(context);
    }

    public JFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mFlRoot = findViewById(R.id.flRoot);
        mLoadingDiv = findViewById(R.id.llLoadingDiv);
        mJtvHint = (JTextView) findViewById(R.id.jtvHint);
        mJtvRetry = (JTextView) findViewById(R.id.jtvRetry);

        setOnClickListener(v -> {
            if (isFailed() && mOnRetryLisn != null) {
                mOnRetryLisn.onRetry();
            }
        });
    }

    public void loading() {
        ViewUtil.hideView(mJtvRetry);
        switchLoadingView(true);
        ViewUtil.showView(mLoadingDiv);
    }

    public void failed() {
        switchLoadingView(false);
        ViewUtil.hideView(mLoadingDiv);
        ViewUtil.showView(mJtvRetry);
    }

    public boolean isFailed() {
        return mJtvRetry.getVisibility() == VISIBLE;
    }

    public void setLoadingView(View v, LayoutParams fllp) {
        mLoadingView = v;
        FrameLayout flDiv = (FrameLayout) findViewById(R.id.flProgressBarDiv);
        if (flDiv.getChildCount() > 0) {
            flDiv.removeAllViews();
        }
        flDiv.addView(v, fllp);
    }

    public void setDarkTheme() {
        setHintTextColor(R.color.white_trans87);
        setBackgroundResource(R.drawable.selector_bg_click_light);
    }

    public void setLightTheme() {
        setHintTextColor(R.color.black_trans54);
        setBackgroundResource(R.drawable.selector_bg_click_dark);
    }

    public void setHintTextColor(@ColorRes int resId) {
        mJtvHint.setTextColor(getResources().getColor(resId));
        mJtvRetry.setTextColor(getResources().getColor(resId));
    }

    public void done() {
        ViewGroup.LayoutParams lp = mFlRoot.getLayoutParams();
        lp.height = 0;
        mFlRoot.setLayoutParams(lp);
    }

    public void ready() {
        ViewGroup.LayoutParams lp = mFlRoot.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mFlRoot.setLayoutParams(lp);
    }

    public void switchLoadingView(boolean startAnim) {
        if (startAnim) {
            ViewUtil.showView(mLoadingView);
        } else {
            ViewUtil.hideView(mLoadingView);
        }
    }

    public interface OnRetryListener {
        void onRetry();
    }

    public void setOnRetryListener(OnRetryListener lisn) {
        mOnRetryLisn = lisn;
    }
}
