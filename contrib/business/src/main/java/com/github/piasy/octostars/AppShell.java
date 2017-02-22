package com.github.piasy.octostars;

import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDex;
import com.facebook.buck.android.support.exopackage.ExopackageApplication;
import com.github.piasy.octostars.business.BuildConfig;

/**
 * Created by guyacong on 15/10/2.
 */
public class AppShell extends ExopackageApplication {

    private static String sRealAppName = "com.github.piasy.octostars.BootstrapApp";

    private final boolean mIsExoPackage;

    public AppShell() {
        super(sRealAppName, BuildConfig.EXOPACKAGE_FLAGS != 0);
        mIsExoPackage = BuildConfig.EXOPACKAGE_FLAGS != 0;
    }

    @VisibleForTesting
    static void setRealAppName(final String realAppName) {
        sRealAppName = realAppName;
    }

    @Override
    protected void onBaseContextAttached() {
        if (!mIsExoPackage) {
            MultiDex.install(this);
        }
    }
}
