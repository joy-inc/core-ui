package com.joy.ui.view;

/**
 * Created by Daisw on 2017/5/15.
 */

public class LoadMore {

    public enum Theme {LIGHT, DARK}

    public interface OnLoadMoreListener {
        void onRefresh(boolean isAuto);
    }
}
