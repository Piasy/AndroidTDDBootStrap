package com.github.piasy.bootstrap;

import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDex;
import com.facebook.buck.android.support.exopackage.ExopackageApplication;

/**
 * Created by guyacong on 15/10/2.
 */
public class AppShell extends ExopackageApplication {

    @VisibleForTesting
    static String sRealAppName = "com.github.piasy.bootstrap.BootstrapApp";

    private final boolean mIsExoPackage;

    public AppShell() {
        super(sRealAppName, BuildConfig.EXOPACKAGE_FLAGS != 0);
        mIsExoPackage = BuildConfig.EXOPACKAGE_FLAGS != 0;
    }

    @Override
    protected void onBaseContextAttached() {
        if (!mIsExoPackage) {
            MultiDex.install(this);
        }
    }
}
