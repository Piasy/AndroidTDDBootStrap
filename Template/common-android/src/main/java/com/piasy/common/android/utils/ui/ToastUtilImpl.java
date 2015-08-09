package com.piasy.common.android.utils.ui;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class ToastUtilImpl implements ToastUtil {

    private final Context mContext;

    public ToastUtilImpl(Context context) {
        mContext = context;
    }

    @Override
    public void makeToast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void makeToast(@StringRes int contentResId) {
        Toast.makeText(mContext, contentResId, Toast.LENGTH_LONG).show();
    }
}
