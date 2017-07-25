package com.joy.ui;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Daisw on 2017/7/9.
 */

@IntDef({TipType.NULL, TipType.EMPTY, TipType.ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface TipType {
    int NULL = 0;
    int EMPTY = 1;
    int ERROR = 2;
}
