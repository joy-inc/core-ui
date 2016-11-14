package com.joy.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.joy.ui.R;
import com.joy.ui.utils.DimenCons;
import com.joy.utils.LayoutInflater;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Daisw on 16/4/27.
 */
public class JToolbar extends Toolbar implements DimenCons {

    public JToolbar(Context context) {
        this(context, null);
    }

    public JToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public JToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageButton setTitleLogo(@DrawableRes int resId) {
        return setTitleLogo(ContextCompat.getDrawable(getContext(), resId));
    }

    public ImageButton setTitleLogo(@NonNull Drawable drawable) {
        return addTitleLeftView(drawable, null);
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId) {
        return addTitleLeftView(resId, null);
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId, OnClickListener lisn) {
        return addTitleLeftView(ContextCompat.getDrawable(getContext(), resId), lisn);
    }

    public ImageButton addTitleLeftView(@NonNull Drawable drawable, OnClickListener lisn) {
        ImageButton ib = LayoutInflater.inflate(getContext(), R.layout.lib_view_toolbar_imagebutton);
        ib.setImageDrawable(drawable);
        return (ImageButton) addTitleLeftView(ib, lisn);
    }

    public TextView addTitleLeftTextView(@StringRes int resId, OnClickListener lisn) {
        return addTitleLeftTextView(getResources().getString(resId), lisn);
    }

    public TextView addTitleLeftTextView(CharSequence text, OnClickListener lisn) {
        TextView tv = initDefTextView(text);
        tv.setPadding(0, 0, DP_1_PX * 10, 0);
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
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        addView(v, lp);
        return v;
    }

    public ImageButton addTitleRightView(@DrawableRes int resId) {
        return addTitleRightView(resId, null);
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
