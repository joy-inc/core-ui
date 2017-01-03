package com.joy.ui.widget;

import android.app.Activity;
import android.view.View;

/**
 * 用来包裹指定的view
 *
 * @author yhb
 */
public abstract class ExViewWidget extends ExBaseWidget {

    public ExViewWidget(Activity activity, View contentView) {
        this(activity, contentView, (Object[]) null);
    }

    public ExViewWidget(Activity activity, View contentView, Object... args) {
        super(activity);
        setContentView(contentView);
        onInitView(contentView, args);
    }

    protected abstract void onInitView(View contentView, Object... args);
}
