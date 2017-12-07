package com.joy.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.joy.ui.R;
import com.joy.utils.LayoutInflater;
import com.joy.utils.ReflectionUtil;
import com.joy.utils.TextUtil;
import com.joy.utils.ViewUtil;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.joy.ui.utils.DimenCons.DP;
import static com.joy.ui.utils.DimenCons.HORIZONTAL_MARGINS;

/**
 * Created by Daisw on 16/4/27.
 */
public class JToolbar extends Toolbar {

    public static final int TITLE_GRAVITY_CENTER = 0x01;
    public static final int TITLE_GRAVITY_LEFT = 0x02;
    public static final int TITLE_GRAVITY_RIGHT = 0x03;

    private ImageButton mNavButtonView;
    private ImageView mLogoView;
    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private ActionMenuView mMenuView;

    public JToolbar(Context context) {
        this(context, null);
    }

    public JToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public JToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageButton getNavButtonView() {
        if (mNavButtonView == null) {
            mNavButtonView = ReflectionUtil.getField(Toolbar.class, "mNavButtonView", this);
        }
        return mNavButtonView;
    }

    public ImageView getLogoView() {
        if (mLogoView == null) {
            mLogoView = ReflectionUtil.getField(Toolbar.class, "mLogoView", this);
        }
        return mLogoView;
    }

    public TextView getTitleTextView() {
        if (mTitleTextView == null) {
            mTitleTextView = ReflectionUtil.getField(Toolbar.class, "mTitleTextView", this);
        }
        return mTitleTextView;
    }

    public TextView getSubtitleTextView() {
        if (mSubtitleTextView == null) {
            mSubtitleTextView = ReflectionUtil.getField(Toolbar.class, "mSubtitleTextView", this);
        }
        return mSubtitleTextView;
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(TextUtil.isEmpty(title) ? " " : title);
    }

    public ActionMenuView getMenuView() {
        if (mMenuView == null) {
            mMenuView = ReflectionUtil.getField(Toolbar.class, "mMenuView", this);
        }
        return mMenuView;
    }

    public ImageView setTitleLogo(@DrawableRes int resId) {
        return setTitleLogo(ContextCompat.getDrawable(getContext(), resId));
    }

    public ImageView setTitleLogo(@NonNull Drawable drawable) {
        setLogo(drawable);
        return getLogoView();
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId) {
        return addTitleLeftView(resId, null);
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId, OnClickListener lisn) {
        return addTitleLeftView(ContextCompat.getDrawable(getContext(), resId), lisn);
    }

    public ImageButton addTitleLeftView(@NonNull Drawable drawable, OnClickListener lisn) {
        setNavigationIcon(drawable);
        setNavigationOnClickListener(lisn);
        ImageButton ib = getNavButtonView();
        ib.setBackgroundResource(SDK_INT >= LOLLIPOP
                ? R.drawable.selector_bg_click_material_light
                : R.drawable.selector_bg_click_light);
        return ib;
    }

    public TextView addTitleLeftTextView(@StringRes int resId, OnClickListener lisn) {
        return addTitleLeftTextView(getResources().getString(resId), lisn);
    }

    public TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn) {
        TextView tv = initDefTextView(text);
        tv.setPadding(0, 0, DP(10), 0);
        return (TextView) addTitleLeftView(tv, lisn);
    }

    public View addTitleLeftView(View v, OnClickListener lisn) {
        if (lisn != null) {
            v.setOnClickListener(lisn);
        }
        addView(v);
        return v;
    }

    public TextView addTitleMiddleView(@StringRes int resId) {
        return addTitleMiddleView(getResources().getString(resId));
    }

    public TextView addTitleMiddleView(CharSequence text) {
        return addTitleMiddleView(text, null);
    }

    public TextView addTitleMiddleView(@StringRes int resId, OnClickListener lisn) {
        return addTitleMiddleView(getResources().getString(resId), lisn);
    }

    public TextView addTitleMiddleView(CharSequence text, OnClickListener lisn) {
        return (TextView) addTitleMiddleView(initDefTextView(text), lisn);
    }

    public View addTitleMiddleView(View v, OnClickListener lisn) {
        if (lisn != null) {
            v.setOnClickListener(lisn);
        }
        LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(v, lp);
        return v;
    }

    public TextView addTitleRightView(@StringRes int resId) {
        return addTitleRightView(getResources().getString(resId));
    }

    public TextView addTitleRightView(CharSequence text) {
        return addTitleRightView(text, null);
    }

    public TextView addTitleRightView(CharSequence text, OnClickListener lisn) {
        TextView tv = (TextView) addTitleRightView(initDefTextView(text), lisn);
        int insetEnd = getContentInsetEnd();
        if (insetEnd == 0) {
            setContentInsetsRelative(getContentInsetStart(), HORIZONTAL_MARGINS);
        }
        TextView subtitleTextView = getSubtitleTextView();
        if (subtitleTextView == null) {
            setSubtitle(" ");
            subtitleTextView = getSubtitleTextView();
            ViewUtil.goneView(subtitleTextView);
        }
        if (subtitleTextView != null) {
            tv.setTypeface(subtitleTextView.getTypeface());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleTextView.getTextSize());
            tv.setTextColor(subtitleTextView.getTextColors());
        }
        return tv;
    }

    public ImageButton addTitleRightView(@DrawableRes int resId, OnClickListener lisn) {
        return addTitleRightView(ContextCompat.getDrawable(getContext(), resId), lisn);
    }

    public ImageButton addTitleRightView(@NonNull Drawable drawable, OnClickListener lisn) {
        ImageButton ib = LayoutInflater.inflate(getContext(), R.layout.lib_view_toolbar_imagebutton);
        ib.setImageDrawable(drawable);
        return (ImageButton) addTitleRightView(ib, lisn);
    }

    @SuppressLint("RtlHardcoded")
    public View addTitleRightView(View v, OnClickListener lisn) {
        if (lisn != null) {
            v.setOnClickListener(lisn);
        }
        LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        addView(v, lp);
        return v;
    }

    private TextView initDefTextView(CharSequence text) {
        TextView tv = LayoutInflater.inflate(getContext(), R.layout.lib_view_toolbar_textview);
        tv.setText(text);
        return tv;
    }
}
