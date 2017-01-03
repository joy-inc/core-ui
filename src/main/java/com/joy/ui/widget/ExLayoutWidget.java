package com.joy.ui.widget;

import android.app.Activity;
import android.view.View;

/**
 * 用来加载一个单独的View
 *
 * @author yhb
 */
public abstract class ExLayoutWidget extends ExBaseWidget {

    public ExLayoutWidget(Activity activity) {
        this(activity, (Object[]) null);
    }

    public ExLayoutWidget(Activity activity, Object... args) {
        super(activity);
        View contentView = onCreateView(activity, args);
        setContentView(contentView);
    }

    protected abstract View onCreateView(Activity activity, Object... args);
}
