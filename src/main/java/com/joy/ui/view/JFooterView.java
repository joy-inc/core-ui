package com.joy.ui.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.joy.ui.R;
import com.joy.ui.utils.DimenCons;
import com.joy.ui.widget.JTextView;
import com.joy.utils.ViewUtil;

/**
 * Created by KEVIN.DAI on 15/11/20.
 */
public class JFooterView extends LinearLayout {

    private View mRootView, mLoadingDiv;
    private OnRetryListener mOnRetryLisn;
    private JTextView mJtvHint, mJtvRetry;

    public JFooterView(Context context) {

        super(context);
        init(context);
    }

    public JFooterView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        setOrientation(VERTICAL);
        setMinimumWidth(DimenCons.SCREEN_WIDTH);

        mRootView = LayoutInflater.from(context).inflate(R.layout.lib_view_footer, null);
        addView(mRootView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mLoadingDiv = findViewById(R.id.llLoadingDiv);
        mJtvHint = (JTextView) findViewById(R.id.jtvHint);
        mJtvRetry = (JTextView) findViewById(R.id.jtvRetry);

        setOnClickListener(v -> {

            if (isFailed() && mOnRetryLisn != null)
                mOnRetryLisn.onRetry();
        });
    }

    public void loading() {

        ViewUtil.hideView(mJtvRetry);
        ViewUtil.showView(mLoadingDiv);
    }

    public void failed() {

        ViewUtil.hideView(mLoadingDiv);
        ViewUtil.showView(mJtvRetry);
    }

    public boolean isFailed() {

        return mJtvRetry.getVisibility() == VISIBLE;
    }

    public void setLoadingView(View v, FrameLayout.LayoutParams flLp) {

        FrameLayout flDiv = (FrameLayout) findViewById(R.id.flProgressBarDiv);
        if (flDiv.getChildCount() > 0)
            flDiv.removeAllViews();

        flDiv.addView(v, flLp);
    }

    public void setDarkTheme() {

        setHintTextColor(R.color.black_trans54);
    }

    public void setLightTheme() {

        setHintTextColor(R.color.white_trans87);
    }

    public void setHintTextColor(@ColorRes int resId) {

        mJtvHint.setTextColor(getResources().getColor(resId));
        mJtvRetry.setTextColor(getResources().getColor(resId));
    }

    public void done() {

        LayoutParams lp = (LayoutParams) mRootView.getLayoutParams();
        lp.height = 0;
        mRootView.setLayoutParams(lp);
    }

    public void ready() {

        LayoutParams lp = (LayoutParams) mRootView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mRootView.setLayoutParams(lp);
    }

    public interface OnRetryListener {

        void onRetry();
    }

    public void setOnRetryListener(OnRetryListener lisn) {

        mOnRetryLisn = lisn;
    }
}