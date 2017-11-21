package com.joy.ui.view.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

import com.joy.ui.R;
import com.joy.utils.LayoutInflater;

import static com.joy.ui.utils.DimenCons.SCREEN_HEIGHT;
import static com.joy.ui.utils.DimenCons.SCREEN_WIDTH;
import static com.joy.ui.utils.DimenCons.STATUS_BAR_HEIGHT;

/**
 * Created by Daisw on 16/8/4.
 */

public class JBottomSheetDialog extends BottomSheetDialog {

    public JBottomSheetDialog(@NonNull Context context) {
        this(context, R.style.base_light_BottomSheetDialog);
    }

    public JBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(SCREEN_WIDTH, SCREEN_HEIGHT - STATUS_BAR_HEIGHT);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId) {
        return LayoutInflater.inflate(getContext(), layoutResId);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root) {
        return LayoutInflater.inflate(getContext(), layoutResId, root);
    }

    protected final <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.inflate(getContext(), layoutResId, root, attachToRoot);
    }
}
