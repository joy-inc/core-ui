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
        final float scale = getScale();
        final float approximately = Math.floor(scale) < scale ? scale : 0;
        return getScrollY() < Math.floor(getContentHeight() * scale - getMeasuredHeight()) - approximately;
    }

    @Override
    public boolean canScrollDown() {
        return getScrollY() > 0;
    }
}
