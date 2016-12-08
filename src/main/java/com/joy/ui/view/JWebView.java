package com.joy.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.joy.ui.view.viewpager.VerticalViewPager;

/**
 * Created by Daisw on 2016/11/16.
 */

public class JWebView extends WebView implements VerticalViewPager.Scrollable {

    public JWebView(Context context) {
        super(context);
    }

    public JWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canScrollUp() {
        return getScrollY() < getContentHeight() * getScale() - getMeasuredHeight();
    }

    @Override
    public boolean canScrollDown() {
        return getScrollY() > 0;
    }
}
