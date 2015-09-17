/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
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

package com.github.piasy.template;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.piasy.common.android.utils.AndroidUtilsModule;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.base.di.AppComponent;
import com.github.piasy.template.base.di.AppModule;
import com.github.piasy.template.base.di.DaggerAppComponent;
import com.github.piasy.template.base.di.IApplication;
import com.github.promeg.xlog_android.lib.XLogConfig;
import com.google.gson.Gson;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import jonathanfinerty.once.Once;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Custom application class, providing APP wild utility, singleton, state control functions.
 */
public class TemplateApp extends Application implements IApplication {

    private static volatile TemplateApp sInstance;
    @Inject
    Gson mGson;
    @Inject
    ToastUtil mToastUtil;
    @Inject
    ThreeTenABPDelegate mThreeTenABPDelegate;
    private AppComponent mAppComponent;

    /**
     * Get the app wild {@link Application} object access.
     *
     * @return custom app object.
     */
    public static TemplateApp getInstance() {
        return sInstance;
    }

    /**
     * findbugs complains: ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD: Write to static field from
     * instance method
     * read more: http://stackoverflow.com/a/13388964/3077508
     */
    private static void setInstance(final TemplateApp app) {
        sInstance = app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .androidUtilsModule(new AndroidUtilsModule(this))
                .build();
        mAppComponent.inject(this);

        Fresco.initialize(this);
        mThreeTenABPDelegate.init();

        Iconify.with(new MaterialModule());
        Once.initialise(this);

        // Developer
        XLogConfig.config(XLogConfig.newConfigBuilder(this).build());
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }

        // test for di in Application object
        final String test = "{\"login\":\"Piasy\"}";
        mToastUtil.makeToast(mGson.fromJson(test, GithubUser.class).login());

        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Timber.i("AutoBoot UncaughtExceptionHandler: thread " + thread.toString() + ", ex " +
                    ex.toString());
        });
    }

    @Override
    public AppComponent component() {
        return mAppComponent;
    }
}
