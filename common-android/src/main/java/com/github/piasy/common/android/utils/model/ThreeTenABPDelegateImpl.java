package com.github.piasy.common.android.utils.model;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 */
public class ThreeTenABPDelegateImpl implements ThreeTenABPDelegate {
    private final Application mApplication;

    public ThreeTenABPDelegateImpl(Application application) {
        this.mApplication = application;
    }

    @Override
    public void init() {
        AndroidThreeTen.init(mApplication);
    }
}
