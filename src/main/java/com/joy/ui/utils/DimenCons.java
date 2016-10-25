package com.joy.ui.utils;

import com.joy.ui.BaseApplication;
import com.joy.ui.R;
import com.joy.utils.DensityUtil;
import com.joy.utils.DeviceUtil;

/**
 * Created by KEVIN.DAI on 15/7/16.
 */
public interface DimenCons {

    int DP_1_PX = DensityUtil.dip2px(BaseApplication.getContext(), 1);

    int SCREEN_WIDTH = DeviceUtil.getScreenWidth(BaseApplication.getContext());
    int SCREEN_HEIGHT = DeviceUtil.getScreenHeight(BaseApplication.getContext());
    int STATUS_BAR_HEIGHT = DeviceUtil.getStatusBarHeight(BaseApplication.getContext());
    int NAVIGATION_BAR_HEIGHT = DeviceUtil.getNavigationBarHeight(BaseApplication.getContext());
    int SCREEN_HEIGHT_ABSOLUTE = SCREEN_HEIGHT + NAVIGATION_BAR_HEIGHT;

    int TITLE_BAR_HEIGHT = DensityUtil.getDimensionPixelSize(BaseApplication.getContext(), R.dimen.def_toolbar_height);
}
