package com.joy.ui.interfaces;

import android.support.annotation.NonNull;
import android.view.View;

import com.joy.ui.TipType;

/**
 * Created by KEVIN.DAI on 17/11/13.
 */
public interface BaseViewNet<E> extends BaseView<E> {

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
