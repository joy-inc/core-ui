package com.joy.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.joy.ui.R;
import com.joy.ui.utils.DimenCons;

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

//        setLogo(resId);
        return addTitleLeftView(resId, null);
    }

    public ImageButton addTitleLeftView(@DrawableRes int resId, OnClickListener lisn) {

//        setNavigationIcon(resId);
//        setNavigationOnClickListener(lisn);

        ImageButton ib = new ImageButton(getContext(), null, R.style.base_style_toolbar_button_navigation);
//        ib.setImageDrawable(mTintManager.getDrawable(resId));
        ib.setImageResource(resId);
        return (ImageButton) addTitleLeftView(ib, lisn);
    }

    public View addTitleLeftView(View v, OnClickListener lisn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            v.setBackgroundResource(R.drawable.control_background_52dp_material);

        if (lisn != null)
            v.setOnClickListener(lisn);

        LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.gravity = Gravity.LEFT;
        lp.leftMargin = HORIZONTAL_MARGINS;
        addView(v, lp);
        return v;
    }

    public TextView addTitleMiddleView(@StringRes int resId) {

        return addTitleMiddleView(getResources().getString(resId));
    }

    public TextView addTitleMiddleView(String text) {

        return addTitleMiddleView(text, null);
    }

    public TextView addTitleMiddleView(@StringRes int resId, OnClickListener lisn) {

        return addTitleMiddleView(getResources().getString(resId), lisn);
    }

    public TextView addTitleMiddleView(String text, OnClickListener lisn) {

        TextView tvTitle = new TextView(getContext());
        tvTitle.setTextAppearance(getContext(), R.style.base_style_toolbar_title);
        tvTitle.setSingleLine();
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
        tvTitle.setText(text);
        return (TextView) addTitleMiddleView(tvTitle, lisn);
    }

    public View addTitleMiddleView(View v, OnClickListener lisn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            v.setBackgroundResource(R.drawable.control_background_52dp_material);

        if (lisn != null)
            v.setOnClickListener(lisn);

        return addTitleMiddleViewWithLp(v, new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
    }

    public View addTitleMiddleViewWithLp(View v, LayoutParams lp) {

//        lp.gravity = Gravity.CENTER;
        lp.leftMargin = HORIZONTAL_MARGINS;
        addView(v, lp);
        return v;
    }

    public ImageButton addTitleRightView(@DrawableRes int resId) {

        return addTitleRightView(resId, null);
    }

    public ImageButton addTitleRightView(@DrawableRes int resId, OnClickListener lisn) {

        ImageButton ib = new ImageButton(getContext(), null, R.style.base_style_toolbar_button_navigation);
//        ib.setImageDrawable(mTintManager.getDrawable(resId));
        ib.setImageResource(resId);
        return (ImageButton) addTitleRightView(ib, lisn);
    }

    public View addTitleRightView(View v, OnClickListener lisn) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            v.setBackgroundResource(R.drawable.control_background_52dp_material);

        if (lisn != null)
            v.setOnClickListener(lisn);

        LayoutParams lp = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        lp.rightMargin = HORIZONTAL_MARGINS;
        addView(v, lp);
        return v;
    }
}
