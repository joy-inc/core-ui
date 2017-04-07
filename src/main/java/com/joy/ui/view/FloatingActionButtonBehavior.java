package com.joy.ui.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Daisw on 16/7/17.
 */
public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior {

    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton button, View dependency) {
        return dependency instanceof AppBarLayout || super.layoutDependsOn(parent, button, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton floatingActionButton, View dependency) {
        if (dependency instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) dependency;
            LayoutParams lp = (LayoutParams) floatingActionButton.getLayoutParams();
            int distanceToScroll = floatingActionButton.getHeight() + lp.bottomMargin;
            float ratio = ViewCompat.getY(appBarLayout) / (float) appBarLayout.getTotalScrollRange();
            ViewCompat.setTranslationY(floatingActionButton, -distanceToScroll * ratio);
            return true;
        }
        return super.onDependentViewChanged(parent, floatingActionButton, dependency);
    }
}
