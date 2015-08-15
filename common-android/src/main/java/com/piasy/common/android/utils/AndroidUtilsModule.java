package com.piasy.common.android.utils;

import android.content.Context;
import com.piasy.common.android.utils.net.GithubAPIErrorProcessor;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.screen.ScreenUtil;
import com.piasy.common.android.utils.ui.ToastUtil;
import com.piasy.common.android.utils.ui.ToastUtilImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
@Module
public class AndroidUtilsModule {

    @Singleton
    @Provides
    ScreenUtil provideScreenUtil(Context context) {
        return new ScreenUtil(context);
    }

    @Singleton
    @Provides
    ToastUtil provideToastUtil(Context context) {
        return new ToastUtilImpl(context);
    }

    @Singleton
    @Provides
    RxUtil.RxErrorProcessor provideRxErrorProcessor(ToastUtil toastUtil) {
        return new RxUtil(new GithubAPIErrorProcessor(toastUtil)).getRxErrorProcessor();
    }
}
