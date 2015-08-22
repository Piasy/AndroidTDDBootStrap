package com.github.piasy.common.android.utils.ui;

import android.support.annotation.StringRes;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public interface ToastUtil {

    void makeToast(String content);

    void makeToast(@StringRes int contentResId);
}
