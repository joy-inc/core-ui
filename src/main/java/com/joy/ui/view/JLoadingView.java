package com.joy.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;

import com.joy.ui.R;
import com.joy.utils.DensityUtil;

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

    public void hide() {

        setVisibility(INVISIBLE);
    }

    public void gone() {

        setVisibility(GONE);
    }

    public void show() {

        setVisibility(VISIBLE);
    }

    /**
     * 适用于Activity/Fragment页面的加载提示 80dp
     *
     * @param context
     * @return
     */
    public static JLoadingView get(Context context) {

        JLoadingView v = new JLoadingView(context);
        int size = DensityUtil.getDimensionPixelSize(context.getApplicationContext(), R.dimen.def_loading_primary);
        v.setLayoutParams(new LayoutParams(size, size, Gravity.CENTER));
        v.setIndeterminate(true);
        return v;
    }

    /**
     * 适用于列表loadmore 30dp
     *
     * @param context
     * @return
     */
    public static JLoadingView getLoadMore(Context context) {

        JLoadingView v = new JLoadingView(context);
        int size = DensityUtil.getDimensionPixelSize(context.getApplicationContext(), R.dimen.def_loading_loadmore);
        v.setLayoutParams(new LayoutParams(size, size));
        v.setIndeterminate(true);
        return v;
    }
}
