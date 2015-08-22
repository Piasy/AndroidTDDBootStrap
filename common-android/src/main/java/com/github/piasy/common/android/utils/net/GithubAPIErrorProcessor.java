package com.github.piasy.common.android.utils.net;

import com.github.piasy.common.android.utils.ui.ToastUtil;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class GithubAPIErrorProcessor {

    private final ToastUtil mToastUtil;

    public GithubAPIErrorProcessor(ToastUtil toastUtil) {
        mToastUtil = toastUtil;
    }

    public void process(GithubAPIError apiError) {
        mToastUtil.makeToast(apiError.getMessage());
    }
}
