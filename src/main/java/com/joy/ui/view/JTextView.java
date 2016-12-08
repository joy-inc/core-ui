package com.joy.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 全局统一的TextView
 * Created by KEVIN.DAI on 15/11/19.
 */
public class JTextView extends AppCompatTextView {

    public JTextView(Context context) {
        super(context);
        //QaTypeFaceUtil.setHYQiHeiTypeFace(this);
    }

    public JTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //QaTypeFaceUtil.setHYQiHeiTypeFace(this);
    }
}
