package com.joy.ui.utils;

import android.support.annotation.DimenRes;

import com.joy.ui.BaseApplication;
import com.joy.ui.R;
import com.joy.utils.DensityUtil;
import com.joy.utils.DeviceUtil;

/**
 * Created by KEVIN.DAI on 15/7/16.
 */
public class DimenCons {

    public static final int DP_1 = DP(1);
    public static final int DP_8 = DP(8);
    public static final int DP_16 = DP(16);

    public static final int SCREEN_WIDTH = DeviceUtil.getScreenWidth(BaseApplication.getContext());
    public static final int SCREEN_HEIGHT = DeviceUtil.getScreenHeight(BaseApplication.getContext());
    public static final int STATUS_BAR_HEIGHT = DeviceUtil.getStatusBarHeight(BaseApplication.getContext());
    public static final int NAVIGATION_BAR_HEIGHT = DeviceUtil.getNavigationBarHeight(BaseApplication.getContext());
    public static final int SCREEN_HEIGHT_ABSOLUTE = SCREEN_HEIGHT + NAVIGATION_BAR_HEIGHT;

    public static final int TITLE_BAR_HEIGHT = DP_RES(R.dimen.def_toolbar_height);
    public static final int HORIZONTAL_MARGINS = DP_RES(R.dimen.def_horizontal_margins);

    public static int DP(float dp) {
        return DensityUtil.dip2px(BaseApplication.getContext(), dp);
    }

    public static int DP_RES(@DimenRes int id) {
        return DensityUtil.getDimensionPixelSize(BaseApplication.getContext(), id);
    }
}
