package com.joy.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.R;

/**
 * Created by Daisw on 15/4/8.
 */
public class AutoChangeLineLayout extends ViewGroup {

    public int horizontalSpacing;
    public int verticalSpacing;

    public AutoChangeLineLayout(Context context) {
        super(context);
    }

    public AutoChangeLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoChangeLineLayout);
        horizontalSpacing = a.getDimensionPixelSize(R.styleable.AutoChangeLineLayout_horSpacing, 0);
        verticalSpacing = a.getDimensionPixelSize(R.styleable.AutoChangeLineLayout_verSpacing, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int viewGroupWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        int startPosX = getPaddingLeft();
        int startPosY = getPaddingTop();
        int childWidth;
        int childHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            // 如果剩余的空间不够，则移到下一行开始位置
            if (startPosX + childWidth > viewGroupWidth - getPaddingRight()) {
                startPosX = getPaddingLeft();
                startPosY += childHeight + verticalSpacing;
            }
            child.setTag(R.id.tag_autolayout_params_id, new LayoutPosParams(startPosX, startPosY, startPosX + childWidth, startPosY + childHeight));
            startPosX += childWidth + horizontalSpacing;
        }
        int viewGroupHeight = childCount == 0 ? 0 : startPosY + childHeight + getPaddingBottom();
        setMeasuredDimension(viewGroupWidth, viewGroupHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutPosParams posParams = (LayoutPosParams) child.getTag(R.id.tag_autolayout_params_id);
            child.layout(posParams.startPosX, posParams.startPosY, posParams.endPosX, posParams.endPosY);
        }
    }

    private static class LayoutPosParams {
        public int startPosX, startPosY, endPosX, endPosY;

        public LayoutPosParams(int startPosX, int startPosY, int endPosX, int endPosY) {
            this.startPosX = startPosX;
            this.startPosY = startPosY;
            this.endPosX = endPosX;
            this.endPosY = endPosY;
        }
    }
}
