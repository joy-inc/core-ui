package com.joy.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.fragment.BaseUiFragment;

import java.util.List;

/**
 * @author Daisw
 */
public class ExFragmentPagerAdapter<T extends BaseUiFragment> extends FragmentPagerAdapter {

    private List<T> mFragments;
    private boolean mFragmentItemDestoryEnable = true;

    public ExFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public T getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mFragmentItemDestoryEnable) {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public void setFragments(List<T> fragments) {
        mFragments = fragments;
    }

    public void add(int position, T t) {
        if (mFragments != null && t != null) {
            mFragments.add(position, t);
        }
    }

    public void add(T t) {
        if (mFragments != null && t != null) {
            mFragments.add(t);
        }
    }

    public void addAll(List<T> ts) {
        if (ts == null) {
            return;
        }
        if (mFragments == null) {
            mFragments = ts;
        } else {
            mFragments.addAll(ts);
        }
    }

    public void addAll(int position, List<T> ts) {
        if (mFragments != null && ts != null) {
            mFragments.addAll(position, ts);
        }
    }

    public void setFragmentItemDestoryEnable(boolean enable) {
        mFragmentItemDestoryEnable = enable;
    }

    public void clear(ViewPager container) {
        if (mFragments != null) {
            int size = mFragments.size();
            int containerChildCount = container.getChildCount();
            for (int i = 0; i < size && i < containerChildCount; i++) {
                destroyItem(container, i, mFragments.get(i));
            }
            mFragments.clear();
            mFragments = null;
        }
    }

    @Override
    @Deprecated
    public void destroyItem(View container, int position, Object object) {
        if (mFragmentItemDestoryEnable) {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    @Deprecated
    public Object instantiateItem(View container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getLabel();
    }
}
