package com.piasy.template.base.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }
}
