package com.piasy.template;

import com.google.gson.Gson;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.github.promeg.xlog_android.lib.XLogConfig;
import com.piasy.model.entities.GithubUser;
import com.piasy.template.base.di.AppComponent;
import com.piasy.template.base.di.AppModule;
import com.piasy.template.base.di.DaggerAppComponent;
import com.piasy.template.base.di.IApplication;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;
import android.widget.Toast;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class TemplateApp extends Application implements IApplication {

    private static TemplateApp sInstance;
    @Inject
    Gson mGson;
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
                .build();
        mAppComponent.inject(this);

        FlowManager.init(this);
        Fresco.initialize(this);

        // Developer
        XLogConfig.config(XLogConfig.newConfigBuilder(this)
                .build());
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);

        // test
        String test = "{\"login\":\"Piasy\"}";
        Toast.makeText(this, mGson.fromJson(test, GithubUser.class).getLogin(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public AppComponent component() {
        return mAppComponent;
    }
}
