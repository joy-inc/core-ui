package com.joy.ui.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 该ViewPager没有别的，就新增了ViewPager是否开启或关闭滚动的api
 *
 * @author yhb, modify by Daisw
 */
public class JViewPager extends ViewPager {

    private boolean mScrollEnabled = true;

    public JViewPager(Context context) {
        super(context);
    }

    public JViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mScrollEnabled ? super.onTouchEvent(ev) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return mScrollEnabled ? super.onInterceptTouchEvent(ev) : false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setScrollEnabled(boolean enabled) {
        mScrollEnabled = enabled;
    }
}
