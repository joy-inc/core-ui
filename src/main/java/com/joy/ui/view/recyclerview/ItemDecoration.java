package com.joy.ui.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.joy.ui.view.JFooterView;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by KEVIN.DAI on 15/11/25.
 * Modified by KEVIN.DAI on 16/7/11.
 *
 * @blog: http://daishiwen.github.io
 * @email: daishiwen1212@gmail.com
 * @see RecyclerView#addItemDecoration(RecyclerView.ItemDecoration)
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private Builder builder;
    private int prevItemCount;

    private ItemDecoration(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        } else {
            if (builder.orientationParams.orientation == HORIZONTAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }

        final boolean headerDividerEnabled = builder.dividerStyle.headerDividerEnabled;
        final boolean footerDividerEnabled = builder.dividerStyle.footerDividerEnabled;
        final Drawable divider = builder.dividerStyle.divider;
        final int dividerSize = builder.dividerStyle.dividerSize;
        final int marginLeft = builder.marginParams.left;
        final int marginTop = builder.marginParams.top;
        final int marginRight = builder.marginParams.right;
        final int marginBottom = builder.marginParams.bottom;
        final int paddingBottom = builder.paddingParams.bottom;

        final int left = parent.getPaddingLeft() + marginLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() - marginRight;

        final int itemCount = adapter.getItemCount();
        final int footerDividerOffset = getFooterDividerOffset(parent);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int childPosition = parent.getChildLayoutPosition(child);

            if (childPosition == NO_POSITION) {
                continue;
            }

            if (!headerDividerEnabled && !footerDividerEnabled && childPosition >= itemCount - footerDividerOffset) {
                // Don't draw divider for last line if footerDividerEnabled = false
                continue;
            }

            if (headerDividerEnabled && !footerDividerEnabled) {
                if (child instanceof JFooterView) {
                    continue;
                }
            }

//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top;
            if (headerDividerEnabled) {
                top = child.getTop() - dividerSize - marginBottom;
                if (child instanceof JFooterView) {
                    if (((JRecyclerView) parent).isLoadMoreEnable()) {
                        top = child.getTop() - dividerSize;
                    } else {
                        top = child.getTop() - paddingBottom - dividerSize;
                    }
                }
            } else {
                top = child.getBottom() /*+ params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))*/ + marginTop;
            }
            divider.setBounds(left, top, right, top + dividerSize);
            divider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final Drawable divider = builder.dividerStyle.divider;
        final int dividerSize = builder.dividerStyle.dividerSize;
        final int marginLeft = builder.marginParams.left;
        final int marginTop = builder.marginParams.top;
        final int marginBottom = builder.marginParams.bottom;

        final int top = parent.getPaddingTop() + marginTop;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - marginBottom;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() /*+ params.rightMargin + Math.round(ViewCompat.getTranslationX(child))*/ + marginLeft;
            divider.setBounds(left, top, left + dividerSize, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildLayoutPosition(view);
        final int itemCount = state.getItemCount();

        final boolean headerDividerEnabled = builder.dividerStyle.headerDividerEnabled;
        final boolean footerDividerEnabled = builder.dividerStyle.footerDividerEnabled;
        final int dividerSize = builder.dividerStyle.dividerSize;
        final int marginLeft = builder.marginParams.left;
        final int marginTop = builder.marginParams.top;
        final int marginRight = builder.marginParams.right;
        final int marginBottom = builder.marginParams.bottom;
        final int paddingLeft = builder.paddingParams.left;
        final int paddingTop = builder.paddingParams.top;
        final int paddingRight = builder.paddingParams.right;
        int paddingBottom = builder.paddingParams.bottom;

        final RecyclerView.LayoutManager lm = parent.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            int spanCount = getSpanCount(parent);
            if (isFirstRow(position, spanCount)) {
                outRect.set(0, paddingTop, 0, marginBottom);
            } else if (isLastRow(position, spanCount, itemCount)) {
                outRect.set(0, marginTop, 0, paddingBottom);
            } else {
                outRect.set(0, marginTop, 0, marginBottom);
            }
        } else if (lm instanceof LinearLayoutManager) {
            if (builder.orientationParams.orientation == HORIZONTAL) {
                if (!(view instanceof JFooterView)) {
                    if (position == 0) {
                        if (headerDividerEnabled) {
                            outRect.set(paddingLeft, marginBottom + dividerSize + paddingTop, paddingRight, dividerSize + marginBottom);
                        } else {
                            outRect.set(paddingLeft, paddingTop, paddingRight, dividerSize + marginTop);
                        }
                    } else {
                        if (itemCount - position == 2) {
                            if (((JRecyclerView) parent).isLoadMoreEnable()) {
                                paddingBottom = 0;
                            }
                            if (footerDividerEnabled) {
                                outRect.set(paddingLeft, marginTop, paddingRight, dividerSize + marginBottom + paddingBottom);
                            } else {
                                outRect.set(paddingLeft, marginTop, paddingRight, paddingBottom);
                            }
                        } else {
                            if (footerDividerEnabled) {
                                outRect.set(paddingLeft, marginTop, paddingRight, dividerSize + marginBottom);
                            } else {
                                outRect.set(paddingLeft, itemCount > prevItemCount ? marginTop + marginBottom + dividerSize : marginTop, paddingRight, dividerSize + marginBottom);
                            }
                        }
                    }
                }
                prevItemCount = itemCount;
            } else {
                outRect.set(0, 0, dividerSize + marginLeft + marginRight, 0);
            }
        }
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            return ((GridLayoutManager) lm).getSpanCount();
        } else if (lm instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) lm).getSpanCount();
        } else {
            throw new UnsupportedOperationException("the GridDividerItemDecoration can only be used in " +
                    "the RecyclerView which use a GridLayoutManager or StaggeredGridLayoutManager");
        }
    }

    private boolean isFirstRow(int position, int spanCount) {
        return position < spanCount;
    }

    private boolean isLastRow(int position, int spanCount, int childCount) {
        int totalRow = (childCount / spanCount) + ((childCount % spanCount > 0) ? 1 : 0);
        return position >= (totalRow - 1) * spanCount;
    }

    private int getHeaderDividerOffset(RecyclerView parent) {
        int uselessHeadCount = 0;
        if (!builder.dividerStyle.headerDividerEnabled)
            uselessHeadCount++;
        if (parent instanceof JRecyclerView)
            uselessHeadCount += ((JRecyclerView) parent).getHeaderViewsCount();
        return uselessHeadCount;
    }

    private int getFooterDividerOffset(RecyclerView parent) {
        int uselessTailCount = 0;
        if (!builder.dividerStyle.footerDividerEnabled)
            uselessTailCount++;
        if (parent instanceof JRecyclerView)
            uselessTailCount += ((JRecyclerView) parent).getFooterViewsCount();
        return uselessTailCount;
    }

    public static class DividerStyle {
        public Drawable divider;
        public int dividerSize;
        public boolean headerDividerEnabled;
        public boolean footerDividerEnabled;
    }

    public static class OrientationParams {
        public int orientation = HORIZONTAL;
    }

    public static class MarginParams {
        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    public static class PaddingParams {
        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    public static class Builder {
        public DividerStyle dividerStyle;
        public OrientationParams orientationParams;
        public MarginParams marginParams;
        public PaddingParams paddingParams;

        @SuppressWarnings("ResourceType")
        private Builder(Context context) {
            dividerStyle = new DividerStyle();
            orientationParams = new OrientationParams();
            marginParams = new MarginParams();
            paddingParams = new PaddingParams();

            final TypedArray ta = context.obtainStyledAttributes(new int[]{
                    android.R.attr.divider,
                    android.R.attr.dividerHeight,
                    android.R.attr.headerDividersEnabled,
                    android.R.attr.footerDividersEnabled
            });
            dividerStyle.divider = ta.getDrawable(0);
            dividerStyle.dividerSize = ta.getDimensionPixelSize(1, 0);
            dividerStyle.headerDividerEnabled = ta.getBoolean(2, false);
            dividerStyle.footerDividerEnabled = ta.getBoolean(3, false);
            ta.recycle();
        }

        public Builder orientation(int orientation) {
            if (orientation != HORIZONTAL && orientation != VERTICAL)
                throw new IllegalArgumentException("invalid orientation");
            orientationParams.orientation = orientation;
            return this;
        }

        public Builder divider(Drawable d) {
            dividerStyle.divider = d;
            return this;
        }

        public Builder dividerSize(int size) {
            dividerStyle.dividerSize = size;
            return this;
        }

        public Builder dividerHeaderEnable(boolean enable) {
            dividerStyle.headerDividerEnabled = enable;
            return this;
        }

        public Builder dividerFooterEnable(boolean enable) {
            dividerStyle.footerDividerEnabled = enable;
            return this;
        }

        public Builder verticalSpace(int space) {
            marginTop(space / 2);
            marginBottom(space / 2);
            return this;
        }

        public Builder marginLeft(int left) {
            marginParams.left = left;
            return this;
        }

        public Builder marginTop(int top) {
            marginParams.top = top;
            return this;
        }

        public Builder marginRight(int right) {
            marginParams.right = right;
            return this;
        }

        public Builder marginBottom(int bottom) {
            marginParams.bottom = bottom;
            return this;
        }

        public Builder margin(int left, int top, int right, int bottom) {
            marginLeft(left);
            marginTop(top);
            marginRight(right);
            marginBottom(bottom);
            return this;
        }

        public Builder margin(int margin) {
            margin(margin, margin, margin, margin);
            return this;
        }

        public Builder margin(int horizontal, int vertical) {
            margin(horizontal, vertical, horizontal, vertical);
            return this;
        }

        public Builder paddingParentLeft(int left) {
            paddingParams.left = left;
            return this;
        }

        public Builder paddingParentTop(int top) {
            paddingParams.top = top;
            return this;
        }

        public Builder paddingParentRight(int right) {
            paddingParams.right = right;
            return this;
        }

        public Builder paddingParentBottom(int bottom) {
            paddingParams.bottom = bottom;
            return this;
        }

        public Builder paddingParent(int left, int top, int right, int bottom) {
            paddingParentLeft(left);
            paddingParentTop(top);
            paddingParentRight(right);
            paddingParentBottom(bottom);
            return this;
        }

        public Builder paddingParent(int padding) {
            paddingParent(padding, padding, padding, padding);
            return this;
        }

        public Builder paddingParent(int horizontal, int vertical) {
            paddingParent(horizontal, vertical, horizontal, vertical);
            return this;
        }

        public ItemDecoration build() {
            return new ItemDecoration(this);
        }
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }
}
