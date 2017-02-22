package com.github.piasy.bootstrap.mocks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

public class DebugSplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());
        Iconify.with(new MaterialModule());
    }
}
