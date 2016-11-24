/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.bootstrap;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.facebook.buck.android.support.exopackage.DefaultApplicationLike;
import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.piasy.bootstrap.analytics.AppBlockCanaryContext;
import com.github.piasy.bootstrap.di.AppComponent;
import com.github.piasy.bootstrap.di.AppModule;
import com.github.piasy.bootstrap.di.DaggerAppComponent;
import com.github.piasy.bootstrap.di.IApplication;
import com.nshmura.strictmodenotifier.StrictModeNotifier;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Custom application class, providing APP wild utility, singleton, state control functions.
 */
public class BootstrapApp extends DefaultApplicationLike implements IApplication {

    @SuppressLint("StaticFieldLeak")
    private static BootstrapApp sInstance;

    @VisibleForTesting
    final Application mApplication;

    private AppComponent mAppComponent;

    public BootstrapApp(final Application application) {
        super();
        mApplication = application;
    }

    public static BootstrapApp get() {
        return sInstance;
    }

    public static Application application() {
        return sInstance.mApplication;
    }

    private static void setInstance(final BootstrapApp instance) {
        sInstance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            // developer tools
            AndroidDevMetrics.initWith(mApplication);
            Stetho.initialize(Stetho.newInitializerBuilder(mApplication)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(mApplication))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mApplication))
                    .build());
            LeakCanary.install(mApplication);

            StrictModeNotifier.install(mApplication);
            new Handler().post(() -> {
                final StrictMode.ThreadPolicy threadPolicy
                        = new StrictMode.ThreadPolicy.Builder().detectAll()
                        .permitDiskReads()
                        .permitDiskWrites()
                        .penaltyLog() // Must!
                        .build();
                StrictMode.setThreadPolicy(threadPolicy);

                final StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder().detectAll()
                        .penaltyLog() // Must!
                        .build();
                StrictMode.setVmPolicy(vmPolicy);
            });

            new ANRWatchDog().start();
            BlockCanary.install(mApplication, new AppBlockCanaryContext()).start();
        }

        mAppComponent = createComponent();
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(mApplication))
                .build();
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return mAppComponent;
    }
}
