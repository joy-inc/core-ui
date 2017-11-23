package com.joy.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;

import com.joy.ui.R;

import static com.joy.ui.utils.DimenCons.DP_RES;

/**
 * 全局统一的LoadingView
 * Created by KEVIN.DAI on 15/11/19.
 */
public class JLoadingView extends ProgressBar {

    public JLoadingView(Context context) {
        super(context);
    }

    public JLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 适用于Activity/Fragment页面的加载提示 80dp
     *
     * @param context
     * @return
     */
    public static JLoadingView get(Context context) {
        JLoadingView v = new JLoadingView(context);
        v.setLayoutParams(getDefaultFrameLoadingLayoutParams());
        v.setIndeterminate(true);
        return v;
    }

    public static LayoutParams getDefaultFrameLoadingLayoutParams() {
        int size = DP_RES(R.dimen.def_loading_primary);
        LayoutParams lp = new LayoutParams(size, size, Gravity.CENTER);
        return lp;
    }

    /**
     * 适用于列表loadmore 30dp
     *
     * @param context
     * @return
     */
    public static JLoadingView getLoadMore(Context context) {
        JLoadingView v = new JLoadingView(context);
        v.setLayoutParams(getDefaultLoadMoreLayoutParams());
        v.setIndeterminate(true);
        return v;
    }

    public static LayoutParams getDefaultLoadMoreLayoutParams() {
        int size = DP_RES(R.dimen.def_loading_loadmore);
        LayoutParams lp = new LayoutParams(size, size);
        return lp;
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
        }
        if (visibility == VISIBLE) {
            setIndeterminate(true);
        } else {
            setIndeterminate(false);
        }
    }
}
