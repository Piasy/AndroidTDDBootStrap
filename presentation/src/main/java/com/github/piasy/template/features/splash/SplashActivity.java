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

package com.github.piasy.template.features.splash;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.piasy.common.android.jsr310.ThreeTenABPDelegate;
import com.github.piasy.common.di.HasComponent;
import com.github.piasy.template.BuildConfig;
import com.github.piasy.template.app.TemplateApp;
import com.github.piasy.template.base.BaseActivity;
import com.github.piasy.template.features.splash.di.SplashComponent;
import com.github.piasy.template.features.splash.di.SplashModule;
import com.github.promeg.xlog_android.lib.XLogConfig;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import jonathanfinerty.once.Once;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 *
 * Splash activity. Time to init app and handle other Intent action. check app init state.
 */
public class SplashActivity extends BaseActivity implements HasComponent<SplashComponent> {

    private static final String SPLASH_FRAGMENT = "SplashFragment";
    private static final String GITHUB_SEARCH_FRAGMENT = "GithubSearchFragment";

    @Inject
    ThreeTenABPDelegate mThreeTenABPDelegate;
    @Inject
    TemplateApp mApp;
    private SplashComponent mSplashComponent;
    private FragmentManager mFragmentManager;

    @Override
    protected void initializeInjector() {
        mSplashComponent = TemplateApp.get(this)
                .visitorComponent()
                .plus(getActivityModule(), new SplashModule());
        mSplashComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(android.R.id.content, new SplashFragment(), SPLASH_FRAGMENT)
                .commit();

        Observable.just(true).subscribeOn(Schedulers.io()).doOnNext(aBoolean -> {
            Fresco.initialize(mApp);
            mThreeTenABPDelegate.init();

            Iconify.with(new MaterialModule());
            Once.initialise(mApp);

            // Developer
            XLogConfig.config(XLogConfig.newConfigBuilder(mApp).build());
            Stetho.initialize(Stetho.newInitializerBuilder(mApp)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(mApp))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mApp))
                    .build());
            if (BuildConfig.REPORT_CRASH) {
                Fabric.with(mApp, new Crashlytics());
                //Crashlytics.setString();
            }
            LeakCanary.install(mApp);
            if (BuildConfig.DEBUG) {
                Timber.plant(new Timber.DebugTree());
            } else {
                Timber.plant(new Timber.DebugTree());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(success -> {
            mFragmentManager.beginTransaction()
                    .remove(mFragmentManager.findFragmentByTag(SPLASH_FRAGMENT))
                    .add(android.R.id.content, new GithubSearchFragment(), GITHUB_SEARCH_FRAGMENT)
                    .commit();
        });
    }

    @Override
    public SplashComponent getComponent() {
        return mSplashComponent;
    }
}
