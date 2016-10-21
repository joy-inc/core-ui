package com.joy.ui.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.joy.ui.R;
import com.joy.ui.activity.interfaces.BaseViewNet;
import com.joy.ui.view.JLoadingView;
import com.joy.utils.DeviceUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

/**
 * Created by KEVIN.DAI on 16/7/3.
 */
public abstract class BaseHttpUiActivity extends BaseUiActivity implements BaseViewNet {

    private ImageView mIvTip;
    private JLoadingView mLoadingView;
    private int mTipResId;
    private final int ERROR_RES_ID = R.drawable.ic_tip_error;
    private final int EMPTY_RES_ID = R.drawable.ic_tip_empty;

    @Override
    protected void wrapContentView(FrameLayout contentParent, View contentView) {
        super.wrapContentView(contentParent, contentView);
        // tip view
        addTipView(contentParent);
        // loading view
        addLoadingView(contentParent);
    }

    @SuppressWarnings("ResourceType")
    private void addTipView(ViewGroup frame) {
        mIvTip = new ImageView(this);
        mIvTip.setScaleType(CENTER_INSIDE);
        mIvTip.setOnClickListener(v -> onTipViewClick());
        hideImageView(mIvTip);
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (!isNoTitle() && !isOverlay()) {
            lp.topMargin = isSystemBarTrans() ? STATUS_BAR_HEIGHT + getToolbarHeight() : getToolbarHeight();
        }
        frame.addView(mIvTip, lp);
    }

    @SuppressWarnings("ResourceType")
    private void addLoadingView(ViewGroup frame) {
        mLoadingView = JLoadingView.get(this);
        mLoadingView.hide();
        LayoutParams lp = (LayoutParams) mLoadingView.getLayoutParams();
        if (!isNoTitle() && !isOverlay()) {
            lp.topMargin = isSystemBarTrans() ? (STATUS_BAR_HEIGHT + getToolbarHeight()) / 2 : getToolbarHeight() / 2;
        }
        frame.addView(mLoadingView, lp);
    }

    private void onTipViewClick() {
        if (getTipType() == TipType.ERROR) {
            if (isNetworkEnable()) {
                doOnRetry();
            } else {
                showToast(R.string.toast_common_no_network);
            }
        }
    }

    protected abstract void doOnRetry();

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hide();
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
        mTipResId = ERROR_RES_ID;
        showImageView(mIvTip, mTipResId);
    }

    @Override
    public void showEmptyTip() {
        mTipResId = EMPTY_RES_ID;
        showImageView(mIvTip, mTipResId);
    }

    @Override
    public void hideTipView() {
        hideImageView(mIvTip);
    }

    @NonNull
    @Override
    public ImageView getTipView() {
        return mIvTip;
    }

    @Override
    public TipType getTipType() {
        if (mIvTip.getDrawable() != null) {
            if (mTipResId == EMPTY_RES_ID) {
                return TipType.EMPTY;
            } else if (mTipResId == ERROR_RES_ID) {
                return TipType.ERROR;
            }
        }
        return TipType.NULL;
    }

    @Override
    public boolean isNetworkEnable() {
        return DeviceUtil.isNetworkEnable(getApplicationContext());
    }
}
