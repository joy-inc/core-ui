package com.joy.ui.activity;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joy.ui.R;
import com.joy.ui.activity.interfaces.BaseView;
import com.joy.ui.utils.DimenCons;
import com.joy.ui.utils.SnackbarUtil;
import com.joy.ui.view.JToolbar;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 基本的UI框架
 * Created by KEVIN.DAI on 15/7/16.
 */
public abstract class BaseUiActivity extends RxAppCompatActivity implements BaseView, DimenCons {

    private FrameLayout mContentParent;
    private View mContentView;
    private JToolbar mToolbar;
    private int mTbHeight;
    private boolean isNoTitle, isOverlay;
    private boolean isSystemBarTrans;

    @Override
    public final void setContentView(@LayoutRes int layoutResId) {
        setContentView(inflateLayout(layoutResId));
    }

    @Override
    public final void setContentView(View contentView) {
        setContentView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public final void setContentView(View contentView, ViewGroup.LayoutParams params) {
        contentView.setLayoutParams(params);
        mContentView = contentView;

        resolveThemeAttribute();

        mContentParent = (FrameLayout) findViewById(Window.ID_ANDROID_CONTENT);
        wrapContentView(mContentParent, contentView);

        initData();
        initTitleView();
        initContentView();
    }

    @SuppressWarnings("ResourceType")
    protected void resolveThemeAttribute() {
        TypedArray a = obtainStyledAttributes(R.styleable.Toolbar);
        isNoTitle = a.getBoolean(R.styleable.Toolbar_noTitle, false);
        isOverlay = a.getBoolean(R.styleable.Toolbar_overlay, false);
        mTbHeight = a.getDimensionPixelSize(R.styleable.Toolbar_android_minHeight, TITLE_BAR_HEIGHT);
        a.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(android.R.style.Theme, typedValue, true);
            int[] attrs = new int[]{android.R.attr.windowTranslucentStatus, android.R.attr.windowTranslucentNavigation};
            TypedArray typedArray = obtainStyledAttributes(typedValue.resourceId, attrs);
            boolean isStatusTrans = typedArray.getBoolean(0, false);
            boolean isNavigationTrans = typedArray.getBoolean(1, false);
            isSystemBarTrans = isStatusTrans || isNavigationTrans;
            typedArray.recycle();
        }
    }

    @SuppressWarnings("ResourceType")
    protected void wrapContentView(FrameLayout contentParent, View contentView) {
        // add transition animation
//        LayoutTransition lt = new LayoutTransition();
//        lt.setDuration(100);
//        rootView.setLayoutTransition(lt);

        contentParent.addView(contentView);
        LayoutParams contentLp = getContentViewLp();

        if (isNoTitle) {
            contentLp.topMargin = isSystemBarTrans ? -STATUS_BAR_HEIGHT : 0;
        } else {
            contentLp.topMargin = isOverlay ? isSystemBarTrans ? -STATUS_BAR_HEIGHT : 0 : isSystemBarTrans ? STATUS_BAR_HEIGHT + mTbHeight : mTbHeight;
            // toolbar
            mToolbar = inflateLayout(R.layout.lib_view_toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            LayoutParams toolbarLp = new LayoutParams(MATCH_PARENT, mTbHeight);
            toolbarLp.topMargin = isSystemBarTrans ? STATUS_BAR_HEIGHT : 0;
            toolbarLp.gravity = Gravity.TOP;
            contentParent.addView(mToolbar, toolbarLp);
        }
    }

    protected void initData() {
    }

    protected void initTitleView() {
    }

    protected void initContentView() {
    }

    protected final FrameLayout getContentParent() {
        return mContentParent;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected final void setBackground(Drawable background) {
        mContentParent.setBackground(background);
    }

    protected final void setBackgroundResource(@DrawableRes int resId) {
        mContentParent.setBackgroundResource(resId);
    }

    protected final void setBackgroundColor(@ColorInt int color) {
        mContentParent.setBackgroundColor(color);
    }

    protected final View getContentView() {
        return mContentView;
    }

    protected final LayoutParams getContentViewLp() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    protected final JToolbar getToolbar() {
        return mToolbar;
    }

    protected final LayoutParams getToolbarLp() {
        return (LayoutParams) mToolbar.getLayoutParams();
    }

    protected final int getToolbarHeight() {
        return mTbHeight;
    }

    protected final boolean isNoTitle() {
        return isNoTitle;
    }

    protected final boolean isOverlay() {
        return isOverlay;
    }

    protected final boolean isSystemBarTrans() {
        return isSystemBarTrans;
    }

    protected final void setStatusBarColorResId(@ColorRes int colorResId) {
        setStatusBarColor(getResources().getColor(colorResId));
    }

    protected final void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    protected final void setNavigationBarColorResId(@ColorRes int colorResId) {
        setNavigationBarColor(getResources().getColor(colorResId));
    }

    protected final void setNavigationBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }
    }

    protected final void setTitleBgColorResId(@ColorRes int colorResId) {
        setTitleBgColor(getResources().getColor(colorResId));
    }

    protected final void setTitleBgColor(@ColorInt int color) {
        mToolbar.setBackgroundColor(color);
    }

    protected final void setTitleBarAlpha(int alpha) {
        mToolbar.getBackground().setAlpha(alpha);
    }

    protected final void setTitleText(@StringRes int resId) {
        setTitleText(getString(resId));
    }

    protected final void setTitleText(String text) {
        mToolbar.setTitle(text);
    }

    protected final void setSubtitle(@StringRes int resId) {
        setSubtitle(getString(resId));
    }

    protected final void setSubtitle(String text) {
        mToolbar.setSubtitle(text);
    }

    protected final void setTitleTextColor(@ColorInt int color) {
        mToolbar.setTitleTextColor(color);
    }

    protected final void setSubtitleTextColor(@ColorInt int color) {
        mToolbar.setSubtitleTextColor(color);
    }

    protected final ImageButton setTitleLogo(@DrawableRes int resId) {
        return mToolbar.setTitleLogo(resId);
    }

    protected final ImageButton addTitleLeftBackView() {
        return addTitleLeftBackView(R.drawable.ic_arrow_back_white_24dp);
    }

    protected final ImageButton addTitleLeftBackView(OnClickListener lisn) {
        return addTitleLeftView(R.drawable.ic_arrow_back_white_24dp, lisn);
    }

    protected final ImageButton addTitleLeftBackView(@DrawableRes int resId) {
        return addTitleLeftView(resId, v -> finish());
    }

    protected final ImageButton addTitleLeftView(@DrawableRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleLeftView(resId, lisn);
    }

    public TextView addTitleLeftTextView(@StringRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleLeftTextView(resId, lisn);
    }

    public TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn) {
        return mToolbar.addTitleLeftTextView(text, lisn);
    }

    protected final TextView addTitleMiddleView(@StringRes int resId) {
        return mToolbar.addTitleMiddleView(resId);
    }

    protected final TextView addTitleMiddleView(CharSequence text) {
        return mToolbar.addTitleMiddleView(text);
    }

    protected final TextView addTitleMiddleView(@StringRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(resId, lisn);
    }

    protected final TextView addTitleMiddleView(CharSequence text, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(text, lisn);
    }

    protected final View addTitleMiddleView(View v, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(v, lisn);
    }

    protected final ImageButton addTitleRightMoreView(OnClickListener lisn) {
        return addTitleRightView(R.drawable.ic_more_vert_white_24dp, lisn);
    }

    protected final ImageButton addTitleRightView(@DrawableRes int resId) {
        return mToolbar.addTitleRightView(resId);
    }

    protected final ImageButton addTitleRightView(@DrawableRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleRightView(resId, lisn);
    }

    protected final View addTitleRightView(View v, OnClickListener lisn) {
        return mToolbar.addTitleRightView(v, lisn);
    }

    /**
     * fragment activity part
     */
    protected final void addFragment(Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(f, tag).commitAllowingStateLoss();
        }
    }

    protected final void addFragment(int frameId, Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(frameId, f).commitAllowingStateLoss();
        }
    }

    protected final void addFragment(int frameId, Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(frameId, f, tag).commitAllowingStateLoss();
        }
    }

    protected final void replaceFragment(int frameId, Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(frameId, f).commitAllowingStateLoss();
        }
    }

    protected final void replaceFragment(int frameId, Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(frameId, f, tag).commitAllowingStateLoss();
        }
    }

    protected final void removeFragment(Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        }
    }

    @Override
    public final void showToast(String text) {
        ToastUtil.showToast(getApplicationContext(), text);
    }

    @Override
    public final void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public final void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    @Override
    @SuppressWarnings("ResourceType")
    public final void showSnackbar(@NonNull CharSequence text) {
        showSnackbar(text, Snackbar.LENGTH_SHORT);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration) {
        showSnackbar(text, duration, -1);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor) {
        showSnackbar(text, duration, -1, textColor);
    }

    @Override
    public final void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor) {
        if (textColor == -1)
            textColor = getResources().getColor(R.color.color_text_primary);
        SnackbarUtil.showSnackbar(getContentView(), text, duration, bgColor, textColor);
    }

    @Override
    public final void showView(View v) {
        ViewUtil.showView(v);
    }

    @Override
    public final void hideView(View v) {
        ViewUtil.hideView(v);
    }

    @Override
    public final void goneView(View v) {
        ViewUtil.goneView(v);
    }

    @Override
    public final void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    @Override
    public final void showImageView(ImageView v, Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    @Override
    public final void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    @Override
    public final void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(this, layoutResId);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(this, layoutResId, root);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(this, layoutResId, root, attachToRoot);
    }
}
