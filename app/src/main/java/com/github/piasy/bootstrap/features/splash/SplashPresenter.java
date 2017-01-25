package com.github.piasy.bootstrap.features.splash;

import android.app.Application;
import com.chenenyu.router.Router;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.github.piasy.bootstrap.BuildConfig;
import com.github.piasy.bootstrap.base.utils.RxUtil;
import com.github.piasy.octostars.BootstrapApp;
import com.github.piasy.octostars.analytics.CrashReportingTree;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import com.github.promeg.androidgitsha.lib.GitShaUtils;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import io.fabric.sdk.android.Fabric;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 20/09/2016.
 */

@ActivityScope
public class SplashPresenter extends YaRxPresenter<SplashView> {
    @Inject
    SplashPresenter() {
        super();
    }

    @Override
    public void attachView(final SplashView view) {
        super.attachView(view);
        addUtilDestroy(Observable
                .fromCallable(() -> {
                    final Application app = BootstrapApp.application();
                    if ("release".equals(BuildConfig.BUILD_TYPE)) {
                        Timber.plant(new CrashReportingTree());
                        Fabric.with(app, new Crashlytics(), new Answers());
                        Crashlytics.setString("git_sha", GitShaUtils.getGitSha(app));
                    } else {
                        Timber.plant(new Timber.DebugTree());
                    }

                    Iconify.with(new MaterialModule());
                    Router.initialize(app);

                    return true;
                })
                .subscribeOn(Schedulers.io())
                .subscribe(success -> {
                    if (isViewAttached()) {
                        getView().finishSplash(success);
                    }
                }, RxUtil.OnErrorLogger));
    }
}
