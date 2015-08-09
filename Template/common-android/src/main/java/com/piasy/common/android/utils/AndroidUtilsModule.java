package com.piasy.common.android.utils;

import com.piasy.common.android.utils.net.GithubAPIErrorProcessor;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.screen.ScreenUtil;
import com.piasy.common.android.utils.ui.ToastUtil;
import com.piasy.common.android.utils.ui.ToastUtilImpl;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
@Module
public class AndroidUtilsModule {

    @Provides
    ScreenUtil provideScreenUtil(Context context) {
        return new ScreenUtil(context);
    }

    @Provides
    ToastUtil provideToastUtil(Context context) {
        return new ToastUtilImpl(context);
    }

    @Provides
    RxUtil.RxErrorProcessor provideRxErrorProcessor(ToastUtil toastUtil) {
        return new RxUtil(new GithubAPIErrorProcessor(toastUtil)).getRxErrorProcessor();
    }

}
