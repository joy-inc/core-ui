package com.joy.ui;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

import com.joy.inject.component.AppComponent;
import com.joy.inject.component.DaggerAppComponent;
import com.joy.inject.module.AppModule;
import com.joy.utils.ToastUtil;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class BaseApplication extends Application {

    private AppComponent mComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        initContext();
    }

    private void initAppComponent() {
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent component() {
        return mComponent;
    }

    private void initContext() {
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    private static void releaseContext() {
        mContext = null;
    }

    public static Resources getAppResources() {
        return mContext.getResources();
    }

    public static String getAppString(@StringRes int resId) {
        return getAppResources().getString(resId);
    }

    public static String getAppString(@StringRes int resId, Object... formatArgs) {
        return getAppResources().getString(resId, formatArgs);
    }

    public static String[] getAppStringArray(@ArrayRes int resId) {
        return getAppResources().getStringArray(resId);
    }

    public static void showToast(@StringRes int stringResId, Object... formatArgs) {
        showToast(getAppString(stringResId, formatArgs));
    }

    public static void showToast(@StringRes int stringResId) {
        showToast(getAppString(stringResId));
    }

    public static void showToast(String text) {
        ToastUtil.showToast(getContext(), text);
    }

    protected static void release() {
        ToastUtil.release();
    }
}
