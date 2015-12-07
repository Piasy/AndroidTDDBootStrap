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
import au.com.ds.ef.EasyFlow;
import au.com.ds.ef.EventEnum;
import au.com.ds.ef.FlowBuilder;
import au.com.ds.ef.StateEnum;
import au.com.ds.ef.StatefulContext;
import au.com.ds.ef.call.ContextHandler;
import au.com.ds.ef.err.LogicViolationError;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.piasy.common.di.HasComponent;
import com.github.piasy.template.BuildConfig;
import com.github.piasy.template.R;
import com.github.piasy.template.app.TemplateApp;
import com.github.piasy.template.base.BaseActivity;
import com.github.piasy.template.features.splash.di.SplashComponent;
import com.github.piasy.template.features.splash.di.SplashModule;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import jonathanfinerty.once.Once;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static au.com.ds.ef.FlowBuilder.on;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 *
 * Splash activity. Init app and handle other Intent action. I imitate the way in
 * <a href="http://frogermcs.github.io/dagger-graph-creation-performance/">frogermcs'  blog:
 * Dagger
 * 2 - graph creation performance</a> to avoid activity state loss.
 */
@SuppressWarnings({
        "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity",
        "PMD.ModifiedCyclomaticComplexity"
})
public class SplashActivity extends BaseActivity implements HasComponent<SplashComponent> {

    private static final String SPLASH_FRAGMENT = "SplashFragment";
    private static final String GITHUB_SEARCH_FRAGMENT = "GithubSearchFragment";
    private static final int TIME = 10000;
    private static final String TAG = "SplashActivity";
    @Inject
    TemplateApp mApp;
    private final StatefulContext mStatefulContext = new StatefulContext();
    private SplashComponent mSplashComponent;
    private FragmentManager mFragmentManager;
    private EasyFlow<StatefulContext> mFlow;
    private boolean mIsPaused;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(android.R.id.content, new SplashFragment(), SPLASH_FRAGMENT)
                .commit();

        initFlow();
        mFlow/*.trace()*/.start(mStatefulContext);
    }

    @Override
    protected void initializeInjector() {
        mSplashComponent = TemplateApp.get(this)
                .visitorComponent()
                .plus(getActivityModule(), new SplashModule());
        mSplashComponent.inject(this);
    }

    private void initFlow() {
        createFlow();

        initFlowEvent();
    }

    private void initFlowEvent() {
        mFlow.whenEnter(State.Start, new ContextHandler<StatefulContext>() {
            @Override
            public void call(final StatefulContext context) {
                context.setState(State.Start);
                Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(final Subscriber<? super Boolean> subscriber) {
                        try {
                            mFlow.trigger(Event.Initialize, mStatefulContext);
                        } catch (LogicViolationError logicViolationError) {
                            Timber.e(logicViolationError, TAG);
                        }
                        if (BuildConfig.DEBUG) {
                            Timber.plant(new Timber.DebugTree());
                        } else {
                            Timber.plant(new Timber.DebugTree());
                        }

                        Fresco.initialize(mApp);
                        Iconify.with(new MaterialModule());
                        Once.initialise(mApp);

                        // Developer
                        //XLogConfig.config(XLogConfig.newConfigBuilder(mApp).build());
                        // simulate heavy library initialization
                        try {
                            Thread.sleep(TIME);
                        } catch (InterruptedException e) {
                            Timber.e(e, TAG);
                        }
                        Stetho.initialize(Stetho.newInitializerBuilder(mApp)
                                .enableDumpapp(Stetho.defaultDumperPluginsProvider(mApp))
                                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mApp))
                                .build());
                        if (BuildConfig.REPORT_CRASH) {
                            Fabric.with(mApp, new Crashlytics());
                            //Crashlytics.setString();
                        }
                        if (BuildConfig.INSTALL_LEAK_CANARY) {
                            LeakCanary.install(mApp);
                        }
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io()).subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(final Boolean aBoolean) {
                        try {
                            mFlow.trigger(Event.Finish, mStatefulContext);
                        } catch (LogicViolationError logicViolationError) {
                            Timber.e(logicViolationError, TAG);
                        }
                    }
                });
            }
        }).whenEnter(State.Transaction, new ContextHandler<StatefulContext>() {
            @Override
            public void call(final StatefulContext context) {
                context.setState(State.Transaction);
                mFragmentManager.beginTransaction()
                        .remove(mFragmentManager.findFragmentByTag(SPLASH_FRAGMENT))
                        .add(android.R.id.content, new GithubSearchFragment(),
                                GITHUB_SEARCH_FRAGMENT)
                        .commit();
            }
        });
    }

    private void createFlow() {
        mFlow = FlowBuilder.from(State.Start)
                .transit(on(Event.Initialize).to(State.Initializing)
                        .transit(on(Event.Finish).finish(State.Transaction),
                                on(Event.Pause).to(State.Wait4InitializedAndResume)
                                        .transit(on(Event.Resume).to(State.Initializing),
                                                on(Event.Finish).to(State.Wait4Resume)
                                                        .transit(on(Event.Resume).finish(
                                                                State.Transaction)))))
                .executor(new UiThreadExecutor());
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mIsPaused) {
            try {
                mFlow.trigger(Event.Resume, mStatefulContext);
            } catch (LogicViolationError logicViolationError) {
                Timber.e(logicViolationError, TAG);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPaused = true;
        try {
            mFlow.trigger(Event.Pause, mStatefulContext);
        } catch (LogicViolationError logicViolationError) {
            Timber.e(logicViolationError, TAG);
        }
    }

    @Override
    public SplashComponent getComponent() {
        return mSplashComponent;
    }

    /**
     * Init state enum.
     * TODO modify EasyFlow to avoid enum.
     */
    enum State implements StateEnum {
        Start, Initializing, Wait4InitializedAndResume, Wait4Resume, Transaction
    }

    /**
     * Init event enum.
     * TODO modify EasyFlow to avoid enum.
     */
    enum Event implements EventEnum {
        Initialize, Pause, Resume, Finish
    }
}
