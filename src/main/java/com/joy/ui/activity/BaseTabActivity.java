package com.joy.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.FrameLayout;

import com.joy.ui.R;
import com.joy.ui.adapter.ExFragmentPagerAdapter;
import com.joy.ui.fragment.BaseUiFragment;
import com.joy.ui.view.JToolbar;
import com.joy.ui.view.viewpager.JViewPager;
import com.joy.utils.CollectionUtil;

import java.util.List;

/**
 * 基本的UI框架
 * Created by KEVIN.DAI on 15/7/16.
 */
public abstract class BaseTabActivity extends BaseUiActivity {

    protected TabLayout mTabLayout;
    protected FloatingActionButton mFloatActionBtn;
    protected List<? extends BaseUiFragment> mFragments;
    protected int mCurrentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_act_tab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CollectionUtil.isNotEmpty(mFragments)) {
            mFragments.get(mCurrentPosition).onVisible();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            if (mFragments != null) {
                mFragments.clear();
                mFragments = null;
            }
        }
    }

    @Override
    @SuppressWarnings("ResourceType")
    public void wrapContentView(FrameLayout contentParent, View contentView) {
//        super.wrapContentView(contentParent, contentView);
        contentParent.addView(contentView);
        FrameLayout.LayoutParams contentLp = getContentViewLp();
        contentLp.topMargin = isSystemBarTrans() ? -STATUS_BAR_HEIGHT : 0;
    }

    @Override
    protected void initTitleView() {
        // toolbar
        mToolbar = (JToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.initTitleView();
    }

    @Override
    protected void initContentView() {
        mFragments = getFragments();
        // view pager
        ExFragmentPagerAdapter pagerAdapter = new ExFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragmentItemDestoryEnable(isPagerItemDestoryEnable());
        pagerAdapter.setFragments(mFragments);
        JViewPager viewPager = (JViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(getPageChangeListener());
        viewPager.setAdapter(pagerAdapter);
        // tab layout
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setSelectedTabIndicatorHeight(DP_1 * 3);
        mTabLayout.setupWithViewPager(viewPager);
        // float action bar
        mFloatActionBtn = (FloatingActionButton) findViewById(R.id.fab);
        setFloatActionBtnDisable();
    }

    public BaseUiFragment getFragment(int location) {
        return CollectionUtil.isEmpty(mFragments) ? null : mFragments.get(location);
    }

    private OnPageChangeListener getPageChangeListener() {
        return new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mFragments.get(position).onVisible();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    public boolean isPagerItemDestoryEnable() {
        return false;
    }

    public abstract List<? extends BaseUiFragment> getFragments();

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * Sets the tab indicator's color for the currently selected tab.
     *
     * @param color color to use for the indicator
     */
    public void setTabIndicatorColor(@ColorInt int color) {
        mTabLayout.setSelectedTabIndicatorColor(color);
    }

    /**
     * Sets the tab indicator's height for the currently selected tab.
     *
     * @param height height to use for the indicator in pixels
     */
    public void setTabIndicatorHeight(int height) {
        mTabLayout.setSelectedTabIndicatorHeight(height);
    }

    /**
     * Sets the text colors for the different states (normal, selected) used for the tabs.
     */
    public void setTabTextColors(@ColorInt int normalColor, @ColorInt int selectedColor) {
        mTabLayout.setTabTextColors(normalColor, selectedColor);
    }

    public FloatingActionButton getFloatActionBtn() {
        return mFloatActionBtn;
    }

    public void setFloatActionBtnEnable(@DrawableRes int resId, View.OnClickListener lisn) {
        setFloatActionBtnEnable(getResources().getDrawable(resId), lisn);
    }

    public void setFloatActionBtnEnable(Drawable drawable, View.OnClickListener lisn) {
        mFloatActionBtn.setEnabled(true);
        showImageView(mFloatActionBtn, drawable);
        mFloatActionBtn.setOnClickListener(lisn);
    }

    public void setFloatActionBtnDisable() {
        mFloatActionBtn.setEnabled(false);
        hideImageView(mFloatActionBtn);
    }
}
