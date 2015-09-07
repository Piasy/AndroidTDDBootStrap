package com.github.piasy.template;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.piasy.common.android.provider.ProviderModule;
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

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

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
        String test = "{\"login\":\"Piasy\"}";
        mToastUtil.makeToast(mGson.fromJson(test, GithubUser.class).login());

        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Timber.i("AutoBoot UncaughtExceptionHandler: thread " + thread.toString() + ", ex " +
                            ex.toString());
            ex.printStackTrace();
        });
    }

    @Override
    public AppComponent component() {
        return mAppComponent;
    }
}
