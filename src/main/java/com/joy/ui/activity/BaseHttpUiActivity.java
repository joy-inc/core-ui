package com.joy.ui.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.joy.ui.R;
import com.joy.ui.TipType;
import com.joy.ui.activity.interfaces.BaseViewNet;
import com.joy.ui.view.JLoadingView;
import com.joy.utils.DeviceUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by KEVIN.DAI on 16/7/3.
 */
public abstract class BaseHttpUiActivity extends BaseUiActivity implements BaseViewNet {

    protected View mTipView;
    protected @TipType int mTipType;
    protected View mLoadingView;
    protected int LOADING_RES_ID = View.NO_ID;
    protected int ERROR_RES_ID = R.drawable.ic_tip_error;
    protected int EMPTY_RES_ID = R.drawable.ic_tip_empty;

    @Override
    public void resolveThemeAttribute() {
        super.resolveThemeAttribute();
        TypedArray a = obtainStyledAttributes(R.styleable.Theme);
        LOADING_RES_ID = a.getResourceId(R.styleable.Theme_loadingView, View.NO_ID);
        ERROR_RES_ID = a.getResourceId(R.styleable.Theme_errorTip, R.drawable.ic_tip_error);
        EMPTY_RES_ID = a.getResourceId(R.styleable.Theme_emptyTip, R.drawable.ic_tip_empty);
        a.recycle();
    }

    @Override
    public void wrapContentView(FrameLayout contentParent, View contentView) {
        super.wrapContentView(contentParent, contentView);
        addTipView(contentParent);
        addLoadingView(contentParent);
    }

    public void addTipView(FrameLayout contentParent) {
        mTipView = getTipView();
        setTipType(TipType.NULL);
        mTipView.setOnClickListener(v -> onTipViewClick());
        hideView(mTipView);
        contentParent.addView(mTipView);
    }

    @NonNull
    @Override
    public View getTipView() {
        ImageView ivTip = new ImageView(this);
        ivTip.setScaleType(ScaleType.CENTER_INSIDE);
        ivTip.setLayoutParams(getTipViewLp());
        return ivTip;
    }

    @SuppressWarnings("ResourceType")
    public LayoutParams getTipViewLp() {
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (hasTitle() && !isTitleOverlay()) {
            lp.topMargin = isSystemBarTrans() ? STATUS_BAR_HEIGHT + getToolbarHeight() : getToolbarHeight();
        }
        return lp;
    }

    @Override
    @TipType
    public int getTipType() {
        return mTipType;
    }

    @Override
    public void setTipType(int tipType) {
        this.mTipType = tipType;
    }

    public void addLoadingView(FrameLayout contentParent) {
        mLoadingView = getLoadingView();
        hideView(mLoadingView);
        contentParent.addView(mLoadingView, getLoadingViewLp());
    }

    @SuppressWarnings("ResourceType")
    private LayoutParams getLoadingViewLp() {
        LayoutParams lp = (LayoutParams) mLoadingView.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER);
        }
        if (hasTitle() && !isTitleOverlay()) {
            lp.topMargin = isSystemBarTrans() ? (STATUS_BAR_HEIGHT + getToolbarHeight()) / 2 : getToolbarHeight() / 2;
        }
        return lp;
    }

    public View getLoadingView() {
        if (LOADING_RES_ID == View.NO_ID) {
            return JLoadingView.get(this);
        } else {
            ImageView ivLoading = new ImageView(this);
            ivLoading.setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER));
            ivLoading.setImageResource(LOADING_RES_ID);
            return ivLoading;
        }
    }

    public void onTipViewClick() {
        if (getTipType() == TipType.ERROR) {
//            if (isNetworkEnable()) {
                doOnRetry();
//            } else {
//                showToast(R.string.toast_common_no_network);
//            }
        }
    }

    public abstract void doOnRetry();

    @Override
    public void showLoading() {
        if (mLoadingView instanceof ImageView) {
            ImageView loadingIv = (ImageView) mLoadingView;
            Drawable d = loadingIv.getDrawable();
            if (d instanceof AnimationDrawable) {
                AnimationDrawable ad = (AnimationDrawable) d;
                ad.start();
            }
        }
        showView(mLoadingView);
    }

    @Override
    public void hideLoading() {
        hideView(mLoadingView);
        if (mLoadingView instanceof ImageView) {
            ImageView loadingIv = (ImageView) mLoadingView;
            Drawable d = loadingIv.getDrawable();
            if (d instanceof AnimationDrawable) {
                AnimationDrawable ad = (AnimationDrawable) d;
                ad.stop();
            }
        }
    }

    @Override
    public void showContent() {
        showView(getContentView());
    }

    @Override
    public void hideContent() {
        hideView(getContentView());
    }

    @Override
    public void showErrorTip() {
        setTipType(TipType.ERROR);
        if (mTipView instanceof ImageView) {
            showImageView((ImageView) mTipView, ERROR_RES_ID);
        } else {
            showView(mTipView);
        }
    }

    @Override
    public void showEmptyTip() {
        setTipType(TipType.EMPTY);
        if (mTipView instanceof ImageView) {
            showImageView((ImageView) mTipView, EMPTY_RES_ID);
        } else {
            showView(mTipView);
        }
    }

    @Override
    public void hideTipView() {
        hideView(mTipView);
    }

    @DrawableRes
    public int getErrorTipResId() {
        return ERROR_RES_ID;
    }

    @DrawableRes
    public int getEmptyTipResId() {
        return EMPTY_RES_ID;
    }

    @Override
    public boolean isNetworkEnable() {
        return DeviceUtil.isNetworkEnable(getApplicationContext());
    }
}
