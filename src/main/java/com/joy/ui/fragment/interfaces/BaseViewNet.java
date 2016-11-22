package com.joy.ui.fragment.interfaces;

import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by KEVIN.DAI on 16/1/18.
 */
public interface BaseViewNet extends BaseView {

    enum TipType {NULL, EMPTY, ERROR}

    void showLoading();
    void hideLoading();
    void showContent();
    void hideContent();
    void showErrorTip();
    void showEmptyTip();
    void hideTipView();

    @NonNull
    ImageView getTipView();
    TipType getTipType();

    boolean isNetworkEnable();
}
