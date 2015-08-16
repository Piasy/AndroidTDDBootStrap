package com.piasy.template;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.piasy.common.android.utils.ui.ToastUtil;
import com.piasy.model.entities.GithubUser;
import com.piasy.template.base.di.AppComponent;
import com.piasy.template.base.di.AppModule;
import com.piasy.template.base.di.DaggerAppComponent;
import com.piasy.template.base.di.IApplication;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class TemplateApp extends Application implements IApplication {

    private static TemplateApp sInstance;
    @Inject
    Gson mGson;
    @Inject
    ToastUtil mToastUtil;
    private AppComponent mAppComponent;

    public static TemplateApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);

        Fresco.initialize(this);
        AndroidThreeTen.init(this);

        // Developer
        //XLogConfig.config(XLogConfig.newConfigBuilder(this).build());
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);

        // test
        String test = "{\"login\":\"Piasy\"}";
        mToastUtil.makeToast(mGson.fromJson(test, GithubUser.class).login());
    }

    @Override
    public AppComponent component() {
        return mAppComponent;
    }
}
