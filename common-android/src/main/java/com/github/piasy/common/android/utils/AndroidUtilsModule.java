package com.github.piasy.common.android.utils;

import android.content.Context;
import com.github.piasy.common.android.utils.net.GithubAPIErrorProcessor;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.common.android.utils.roms.MiUIUtil;
import com.github.piasy.common.android.utils.roms.RomUtil;
import com.github.piasy.common.android.utils.screen.ScreenUtil;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.github.piasy.common.android.utils.ui.ToastUtilImpl;
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

    @Singleton
    @Provides
    MiUIUtil provideMiUIUtil() {
        return new MiUIUtil(RomUtil.provideRomUtil());
    }
}
