package com.github.piasy.template;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.base.di.AppComponent;
import com.github.piasy.template.base.di.AppModule;
import com.github.piasy.template.base.di.DaggerAppComponent;
import com.github.piasy.template.base.di.IApplication;
import com.google.gson.Gson;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class TemplateApp extends Application implements IApplication {

    private static TemplateApp sInstance;
    @Inject
    Gson mGson;
    @Inject
    ToastUtil mToastUtil;
    @Inject
    ThreeTenABPDelegate mThreeTenABPDelegate;
    private AppComponent mAppComponent;

    public static TemplateApp getInstance() {
        return sInstance;
    }

    public interface StartFrom {
        int UNDEFINED = -1;
        int BOOT = 1;
        int HOME = 2;
    }

    private int mStartFrom = StartFrom.UNDEFINED;

    public void setStartFrom(int startFrom) {
        mStartFrom = startFrom;
    }

    public int getStartFrom() {
        return mStartFrom;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);

        Fresco.initialize(this);
        mThreeTenABPDelegate.init();

        Iconify.with(new MaterialModule());

        // Developer
        //XLogConfig.config(XLogConfig.newConfigBuilder(this).build());
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

        // test
        String test = "{\"login\":\"Piasy\"}";
        mToastUtil.makeToast(mGson.fromJson(test, GithubUser.class).login());

        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Timber.i(
                    "AutoBoot UncaughtExceptionHandler: thread " + thread.toString() + ", ex " +
                            ex.toString());
            ex.printStackTrace();
        });
    }

    @Override
    public AppComponent component() {
        return mAppComponent;
    }
}
