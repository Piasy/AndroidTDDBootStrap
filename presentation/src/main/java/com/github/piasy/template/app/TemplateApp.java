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

package com.github.piasy.template.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.github.piasy.common.android.utils.AndroidUtilsModule;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.app.di.AppComponent;
import com.github.piasy.template.app.di.AppModule;
import com.github.piasy.template.app.di.DaggerAppComponent;
import com.github.piasy.template.app.di.IApplication;
import com.github.piasy.template.app.di.UserComponent;
import com.github.piasy.template.app.di.UserModule;
import com.github.piasy.template.app.di.VisitorComponent;
import com.github.piasy.template.app.di.VisitorModule;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Custom application class, providing APP wild utility, singleton, state control functions.
 */
public class TemplateApp extends Application implements IApplication {

    private AppComponent mAppComponent;
    private VisitorComponent mVisitorComponent;
    private UserComponent mUserComponent;

    /**
     * get app object from context.
     *
     * @param context context.
     * @return TemplateApp object.
     */
    public static TemplateApp get(@NonNull final Context context) {
        return (TemplateApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();

        // TODO if de-comment this line, ClassNotFoundException will be thrown for
        // "RestProvider$RestAdapterHolder"
        //XLogConfig.config(XLogConfig.newConfigBuilder(this).build());
    }

    private void initializeInjector() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .androidUtilsModule(new AndroidUtilsModule(this))
                    .build();
        }
        mVisitorComponent = mAppComponent.plus(new VisitorModule());
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return mAppComponent;
    }

    @NonNull
    @Override
    public VisitorComponent visitorComponent() {
        return mVisitorComponent;
    }

    @NonNull
    @Override
    public UserComponent createUserComponent(final GithubUser user) {
        mUserComponent = mAppComponent.plus(new UserModule(user));
        return mUserComponent;
    }

    @NonNull
    @Override
    public UserComponent userComponent() {
        if (mUserComponent == null) {
            throw new IllegalStateException(
                    "You should call createUserComponent(GithubUser user) at first.");
        }
        return mUserComponent;
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void releaseUserComponent() {
        mUserComponent = null;
    }

    /**
     * set mock component for test.
     *
     * @param appComponent mock component
     */
    void setComponent(@NonNull final AppComponent appComponent) {
        mAppComponent = appComponent;
        mVisitorComponent = mAppComponent.plus(new VisitorModule());
    }
}
