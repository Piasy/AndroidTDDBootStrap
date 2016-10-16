package com.github.piasy.gh;

import android.support.multidex.MultiDex;
import com.facebook.buck.android.support.exopackage.ExopackageApplication;

/**
 * Created by guyacong on 15/10/2.
 */
public class AppShell extends ExopackageApplication {

    private static final String REAL_APP_NAME = "com.github.piasy.gh.BootstrapApp";

    private final boolean mIsExoPackage;

    public AppShell() {
        super(REAL_APP_NAME, BuildConfig.EXOPACKAGE_FLAGS != 0);
        mIsExoPackage = BuildConfig.EXOPACKAGE_FLAGS != 0;
    }

    @Override
    protected void onBaseContextAttached() {
        if (!mIsExoPackage) {
            MultiDex.install(this);
        }
    }
}
