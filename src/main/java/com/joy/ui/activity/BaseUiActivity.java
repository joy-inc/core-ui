package com.joy.ui.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;
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
import com.joy.ui.interfaces.BaseView;
import com.joy.ui.utils.SnackbarUtil;
import com.joy.ui.view.JToolbar;
import com.joy.utils.LayoutInflater;
import com.joy.utils.TextUtil;
import com.joy.utils.ToastUtil;
import com.joy.utils.ViewUtil;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import static android.view.View.NO_ID;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.joy.ui.utils.DimenCons.STATUS_BAR_HEIGHT;
import static com.joy.ui.utils.DimenCons.TITLE_BAR_HEIGHT;
import static com.joy.ui.view.JToolbar.TITLE_GRAVITY_CENTER;
import static com.joy.ui.view.JToolbar.TITLE_GRAVITY_LEFT;
import static com.joy.ui.view.JToolbar.TITLE_GRAVITY_RIGHT;

/**
 * 基本的UI框架
 * Created by KEVIN.DAI on 16/7/3.
 */
public abstract class BaseUiActivity extends RxAppCompatActivity implements BaseView<ActivityEvent> {

    protected FrameLayout mContentParent;
    protected View mContentView;
    protected JToolbar mToolbar;
    protected int mTbHeight;
    protected boolean isNoTitle, isOverlay;
    protected boolean isSystemBarTrans;
    protected int mTitleTextGravity = TITLE_GRAVITY_LEFT;
    protected int mTitleBackIconResId = NO_ID;
    protected int mTitleMoreIconResId = NO_ID;
    protected int mTitleBackgroundResId = NO_ID;
    protected ImageButton mTitleBackView;
    protected ImageButton mTitleMoreView;
    protected TextView mTitleTextView;
    protected String mTitleText, mSubtitleText;

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        setContentView(inflateLayout(layoutResId));
    }

    @Override
    public void setContentView(View contentView) {
        setContentView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    @Override
    public void setContentView(View contentView, ViewGroup.LayoutParams params) {
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
    public void resolveThemeAttribute() {
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

        TypedArray a = obtainStyledAttributes(R.styleable.Toolbar);
        isNoTitle = a.getBoolean(R.styleable.Toolbar_noTitle, false);
        isOverlay = a.getBoolean(R.styleable.Toolbar_overlay, false);
        mTitleText = a.getString(R.styleable.Toolbar_titleText);
        mSubtitleText = a.getString(R.styleable.Toolbar_subtitleText);
        mTbHeight = a.getDimensionPixelSize(R.styleable.Toolbar_titleHeight, TITLE_BAR_HEIGHT);
        if (isSystemBarTrans) {
            mTbHeight += STATUS_BAR_HEIGHT;
        }
        mTitleTextGravity = a.getInt(R.styleable.Toolbar_titleTextGravity, TITLE_GRAVITY_LEFT);
        mTitleBackIconResId = a.getResourceId(R.styleable.Toolbar_titleBackIcon, NO_ID);
        mTitleMoreIconResId = a.getResourceId(R.styleable.Toolbar_titleMoreIcon, NO_ID);
        mTitleBackgroundResId = a.getResourceId(R.styleable.Toolbar_titleBackground, NO_ID);
        a.recycle();
    }

    @SuppressWarnings("ResourceType")
    public void wrapContentView(FrameLayout contentParent, View contentView) {
        // add transition animation
//        LayoutTransition lt = new LayoutTransition();
//        lt.setDuration(100);
//        contentParent.setLayoutTransition(lt);

        contentParent.addView(contentView);

        LayoutParams contentLp = getContentViewLp();
        if (!hasTitle()) {
            contentLp.topMargin = isSystemBarTrans ? -STATUS_BAR_HEIGHT : 0;
        } else {
            contentLp.topMargin = isOverlay ?
                    isSystemBarTrans ? -STATUS_BAR_HEIGHT : 0
                    : mTbHeight;
            // toolbar
            mToolbar = inflateLayout(R.layout.lib_view_toolbar);
            if (isSystemBarTrans) {
                mToolbar.setPadding(0, STATUS_BAR_HEIGHT, 0, 0);
            }
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            contentParent.addView(mToolbar, new LayoutParams(MATCH_PARENT, mTbHeight));
        }
    }

    protected void initData() {
    }

    protected void initTitleView() {
        if (hasTitle()) {
            if (mTitleBackgroundResId != NO_ID) {
                setTitleBgResource(mTitleBackgroundResId);
            }
            if (hasTitleText()) {
                setTitle(mTitleText);
            }
            if (hasSubtitleText()) {
                setSubtitle(mSubtitleText);
            }
            if (hasTitleBack()) {
                mTitleBackView = addTitleBackView(v -> onTitleBackClick(v));
            }
            if (hasTitleMore()) {
                mTitleMoreView = addTitleRightMoreView(v -> onTitleMoreClick(v));
            }
        }
    }

    protected void initContentView() {
    }

    /**
     * @Notice 注意调用时机，在super.initTitleView()之前调用
     */
    public void disableTitleBack() {
        mTitleBackIconResId = NO_ID;
    }

    /**
     * @Notice 注意调用时机，在super.initTitleView()之前调用
     */
    public void disableTitleMore() {
        mTitleMoreIconResId = NO_ID;
    }

    public boolean hasTitleText() {
        return TextUtil.isNotEmpty(mTitleText);
    }

    public boolean hasSubtitleText() {
        return TextUtil.isNotEmpty(mSubtitleText);
    }

    public boolean hasTitleBack() {
        return mTitleBackIconResId != NO_ID;
    }

    public boolean hasTitleMore() {
        return mTitleMoreIconResId != NO_ID;
    }

    public void onTitleBackClick(View v) {
        onBackPressed();
    }

    public void onTitleMoreClick(View v) {
    }

    public ImageButton getTitleBackView() {
        return mTitleBackView;
    }

    public ImageButton getTitleMoreView() {
        return mTitleMoreView;
    }

    public FrameLayout getContentParent() {
        return mContentParent;
    }

    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContentParent.setBackground(background);
        } else {
            mContentParent.setBackgroundDrawable(background);
        }
    }

    public void setBackgroundResource(@DrawableRes int resId) {
        mContentParent.setBackgroundResource(resId);
    }

    public void setBackgroundColor(@ColorInt int color) {
        mContentParent.setBackgroundColor(color);
    }

    public View getContentView() {
        return mContentView;
    }

    public LayoutParams getContentViewLp() {
        return (LayoutParams) mContentView.getLayoutParams();
    }

    public JToolbar getToolbar() {
        return mToolbar;
    }

    public LayoutParams getToolbarLp() {
        return (LayoutParams) mToolbar.getLayoutParams();
    }

    public int getToolbarHeight() {
        return mTbHeight;
    }

    public boolean hasTitle() {
        return !isNoTitle;
    }

    public boolean isTitleOverlay() {
        return isOverlay;
    }

    public boolean isSystemBarTrans() {
        return isSystemBarTrans;
    }

    public void setStatusBarColorResource(@ColorRes int colorResId) {
        setStatusBarColor(getResources().getColor(colorResId));
    }

    public void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

    public void setNavigationBarColorResource(@ColorRes int colorResId) {
        setNavigationBarColor(getResources().getColor(colorResId));
    }

    public void setNavigationBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }
    }

    public void setTitleBgColorResource(@ColorRes int colorResId) {
        setTitleBgColor(getResources().getColor(colorResId));
    }

    public void setTitleBgColor(@ColorInt int color) {
        mToolbar.setBackgroundColor(color);
    }

    public void setTitleBgDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mToolbar.setBackground(drawable);
        } else {
            mToolbar.setBackgroundDrawable(drawable);
        }
    }

    public void setTitleBgResource(@DrawableRes int drawableResId) {
        setTitleBgDrawable(ContextCompat.getDrawable(this, drawableResId));
    }

    /**
     * ToolBar的Background不是色值的情况可以调用此方法，否则请调用{@link #setTitleBarAlpha(int, int)}。
     * 因为直接改变ColorDrawable的alpha值会影响其它地方。
     *
     * @param alpha
     */
    public void setTitleBarAlpha(@IntRange(from = 0x0, to = 0xFF) int alpha) {
        Drawable drawable = mToolbar.getBackground();
        if (drawable != null && !(drawable instanceof ColorDrawable)) {
            drawable.setAlpha(alpha);
        }
    }

    /**
     * @param color
     * @param alpha
     */
    public void setTitleBarAlpha(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int alpha) {
        mToolbar.setBackgroundColor(ColorUtils.setAlphaComponent(color, alpha));
    }

    /**
     * @param titleResId
     * @see {@link android.support.v7.widget.Toolbar#setTitle(int)}
     */
    @Override
    public void setTitle(@StringRes int titleResId) {
        setTitle(getText(titleResId));
    }

    /**
     * @param title
     * @see {@link android.support.v7.widget.Toolbar#setTitle(CharSequence)}
     */
    @Override
    public void setTitle(CharSequence title) {
        if (mTitleTextGravity == TITLE_GRAVITY_CENTER) {
            mTitleTextView = addTitleMiddleView(title);
        } else if (mTitleTextGravity == TITLE_GRAVITY_RIGHT) {
            mTitleTextView = addTitleRightView(title);
        } else {
            mToolbar.setTitle(title);
            mTitleTextView = mToolbar.getTitleTextView();
            return;
        }
        TextView titleTextView = getTitleTextView();
        if (titleTextView != null) {
            mTitleTextView.setTypeface(titleTextView.getTypeface());
        }
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    /**
     * @param textColor
     * @see {@link #setTitleTextColor(int)}
     */
    @Override
    public void setTitleColor(@ColorInt int textColor) {
        setTitleTextColor(textColor);
    }

    public void setTitleTextColor(@ColorInt int color) {
        mToolbar.setTitleTextColor(color);
    }

    public void setSubtitle(@StringRes int resId) {
        setSubtitle(getString(resId));
    }

    public void setSubtitle(String text) {
        mToolbar.setSubtitle(text);
    }

    public TextView getSubtitleTextView() {
        return mToolbar.getSubtitleTextView();
    }

    public void setSubtitleTextColor(@ColorInt int color) {
        mToolbar.setSubtitleTextColor(color);
    }

    public ImageView setTitleLogo(@DrawableRes int resId) {
        return mToolbar.setTitleLogo(resId);
    }

    public ImageView setTitleLogo(@NonNull Drawable drawable) {
        return mToolbar.setTitleLogo(drawable);
    }

    public ImageView getTitleLogoView() {
        return mToolbar.getLogoView();
    }

    public ImageButton addTitleBackView() {
        return addTitleBackView(mTitleBackIconResId == NO_ID ? R.drawable.ic_arrow_back_white_24dp : mTitleBackIconResId);
    }

    public ImageButton addTitleBackView(OnClickListener lisn) {
        return addTitleLeftView(mTitleBackIconResId == NO_ID ? R.drawable.ic_arrow_back_white_24dp : mTitleBackIconResId, lisn);
    }

    public ImageButton addTitleBackView(@DrawableRes int resId) {
        return addTitleLeftView(resId, v -> finish());
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId) {
        return mToolbar.addTitleLeftView(resId);
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleLeftView(resId, lisn);
    }

    public ImageButton addTitleLeftView(@NonNull Drawable drawable, OnClickListener lisn) {
        return mToolbar.addTitleLeftView(drawable, lisn);
    }

    public ImageButton getTitleLeftButtonView() {
        return mToolbar.getNavButtonView();
    }

    public TextView addTitleLeftTextView(@StringRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleLeftTextView(resId, lisn);
    }

    public TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn) {
        return mToolbar.addTitleLeftTextView(text, lisn);
    }

    public TextView addTitleMiddleView(@StringRes int resId) {
        return mToolbar.addTitleMiddleView(resId);
    }

    public TextView addTitleMiddleView(CharSequence text) {
        return mToolbar.addTitleMiddleView(text);
    }

    public TextView addTitleMiddleView(@StringRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(resId, lisn);
    }

    public TextView addTitleMiddleView(CharSequence text, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(text, lisn);
    }

    public View addTitleMiddleView(View v, OnClickListener lisn) {
        return mToolbar.addTitleMiddleView(v, lisn);
    }

    public ImageButton addTitleRightMoreView(OnClickListener lisn) {
        return addTitleRightView(mTitleMoreIconResId == NO_ID ? R.drawable.ic_more_vert_white_24dp : mTitleMoreIconResId, lisn);
    }

    public TextView addTitleRightView(@StringRes int resId) {
        return mToolbar.addTitleRightView(resId);
    }

    public TextView addTitleRightView(CharSequence text) {
        return mToolbar.addTitleRightView(text);
    }

    public TextView addTitleRightView(CharSequence text, OnClickListener lisn) {
        return mToolbar.addTitleRightView(text, lisn);
    }

    public ImageButton addTitleRightView(@DrawableRes int resId, OnClickListener lisn) {
        return mToolbar.addTitleRightView(resId, lisn);
    }

    public ImageButton addTitleRightView(@NonNull Drawable drawable, OnClickListener lisn) {
        return mToolbar.addTitleRightView(drawable, lisn);
    }

    public View addTitleRightView(View v, OnClickListener lisn) {
        return mToolbar.addTitleRightView(v, lisn);
    }

    /**
     * fragment activity part
     */
    public void addFragment(Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(f, tag).commitAllowingStateLoss();
        }
    }

    public void addFragment(int frameId, Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(frameId, f).commitAllowingStateLoss();
        }
    }

    public void addFragment(int frameId, Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().add(frameId, f, tag).commitAllowingStateLoss();
        }
    }

    public void replaceFragment(int frameId, Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(frameId, f).commitAllowingStateLoss();
        }
    }

    public void replaceFragment(int frameId, Fragment f, String tag) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(frameId, f, tag).commitAllowingStateLoss();
        }
    }

    public void removeFragment(Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        }
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showToast(this, text);
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(@StringRes int resId, Object... formatArgs) {
        showToast(getString(resId, formatArgs));
    }

    @Override
    @SuppressWarnings("ResourceType")
    public void showSnackbar(@NonNull CharSequence text) {
        showSnackbar(text, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor) {
        showSnackbar(text, duration, SnackbarUtil.NO_COLOR, textColor);
    }

    @Override
    public void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor) {
        if (textColor == SnackbarUtil.NO_COLOR) {
            textColor = getResources().getColor(R.color.color_text_primary);
        }
        SnackbarUtil.showSnackbar(getContentView(), text, duration, bgColor, textColor);
    }

    @Override
    public void showView(View v) {
        ViewUtil.showView(v);
    }

    @Override
    public void hideView(View v) {
        ViewUtil.hideView(v);
    }

    @Override
    public void goneView(View v) {
        ViewUtil.goneView(v);
    }

    @Override
    public void showImageView(ImageView v, @DrawableRes int resId) {
        ViewUtil.showImageView(v, resId);
    }

    @Override
    public void showImageView(ImageView v, Drawable drawable) {
        ViewUtil.showImageView(v, drawable);
    }

    @Override
    public void hideImageView(ImageView v) {
        ViewUtil.hideImageView(v);
    }

    @Override
    public void goneImageView(ImageView v) {
        ViewUtil.goneImageView(v);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(this, layoutResId);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(this, layoutResId, root);
    }

    @Override
    public <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(this, layoutResId, root, attachToRoot);
    }

    public int getColorInt(@ColorRes int colorResId) {
        return getResources().getColor(colorResId);
    }
}
