package com.joy.ui.activity.interfaces;

import android.support.annotation.NonNull;
import android.view.View;

import com.joy.ui.TipType;

/**
 * Created by KEVIN.DAI on 16/1/18.
 */
public interface BaseViewNet extends BaseView {

    void showLoading();
    void hideLoading();
    void showContent();
    void hideContent();
    void showErrorTip();
    void showEmptyTip();
    void hideTipView();

    @NonNull
    View getTipView();
    void setTipType(@TipType int tipType);
    @TipType
    int getTipType();

    boolean isNetworkEnable();
}
