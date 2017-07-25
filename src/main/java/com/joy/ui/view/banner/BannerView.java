package com.joy.ui.view.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.joy.utils.LogMgr;

import java.lang.reflect.Field;

/**
 * Modified by KEVIN.DAI on 15/12/17.
 */
public class BannerView extends ViewPager {

    public static final int DEFAULT_INTERVAL = 2000;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    /**
     * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     */
    private long interval = DEFAULT_INTERVAL;
    /**
     * auto scroll direction, default is {@link #RIGHT}
     */
    private int direction = RIGHT;
    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true
     */
    private boolean isCycle = true;
    /**
     * whether stop auto scroll when touching, default is true
     */
    private boolean stopScrollWhenTouch = true;
    /**
     * whether animating when auto scroll at the last or first item
     */
    private boolean isBorderAnimation = true;

    private Handler handler;
    private boolean isAutoScroll = false;
    private BannerScroller scroller = null;

    public static final int SCROLL_WHAT = 0;

    public BannerView(Context paramContext) {
        super(paramContext);
        init();
    }

    public BannerView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        handler = new MyHandler();
        setViewPagerScroller();
    }

    /**
     * start auto scroll, first scroll delay time is {@link #getInterval()}
     */
    public void startAutoScroll() {
        if (!isAutoScroll) {
            isAutoScroll = true;
            sendScrollMessage(interval);
        }
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills) {
        if (!isAutoScroll) {
            isAutoScroll = true;
            sendScrollMessage(delayTimeInMills);
        }
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        if (isAutoScroll) {
            isAutoScroll = false;
            handler.removeMessages(SCROLL_WHAT);
        }
    }

    /**
     * set the factor by which the duration of sliding animation will change
     */
    public void setScrollDuration(int duration) {
        scroller.setScrollDuration(duration);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new BannerScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BannerScroller getScroller() {
        return scroller;
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }
        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        if (stopScrollWhenTouch) {
            if (action == MotionEvent.ACTION_DOWN) {
                stopAutoScroll();
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                startAutoScroll();
            }
        }
        return super.onTouchEvent(ev);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL_WHAT:
                    scrollOnce();
                    sendScrollMessage(interval);


                    int count = getChildCount();
                    for (int i = 0; i < count; i++) {
                        if (i == 0){
                            int left = 1440*(getCurrentItem()-1);
                            getChildAt(i).setLeft(left);
                            getChildAt(i).setRight(left+1440);
                            getChildAt(i).setBottom(460);
                        }else if (i == 1){
                            int left = 1440*getCurrentItem();
                            getChildAt(i).setLeft(left);
                            getChildAt(i).setRight(left+1440);
                            getChildAt(i).setBottom(460);
                        }
                        LogMgr.i("daisw", "---====" + getChildAt(i) + "===" + i + "===" + getCurrentItem());
                    }
                default:
                    break;
            }
        }
    }

    /**
     * get auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * get auto scroll direction
     *
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     *
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true
     *
     * @return the isCycle
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * set whether automatic cycle when auto scroll reaching the last or first item, default is true
     *
     * @param isCycle the isCycle to set
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * whether stop auto scroll when touching, default is true
     *
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     *
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    /**
     * whether animating when auto scroll at the last or first item, default is true
     *
     * @return
     */
    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    /**
     * set whether animating when auto scroll at the last or first item, default is true
     *
     * @param isBorderAnimation
     */
    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogMgr.e("daisw", "onAttachedToWindow onAttachedToWindow onAttachedToWindow");
        startAutoScroll(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogMgr.e("daisw", "onDetachedFromWindow onDetachedFromWindow onDetachedFromWindow");
        stopAutoScroll();
    }
}
