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

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.piasy.bootstrap.di.AppComponent;
import com.github.piasy.bootstrap.di.AppModule;
import com.github.piasy.bootstrap.di.DaggerMockAppComponent;
import com.github.piasy.bootstrap.di.MockAppComponent;
import com.github.piasy.bootstrap.di.MockProviderConfigModule;
import com.google.gson.Gson;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 3/7/16.
 */
public class MockBootstrapApp extends BootstrapApp {

    private static MockBootstrapApp sInstance;

    @Inject
    public Gson mGson;

    public MockBootstrapApp(final Application application) {
        super(application);
    }

    private static void setInstance(final MockBootstrapApp instance) {
        sInstance = instance;
    }

    public static MockBootstrapApp get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Fresco.initialize(mApplication);
        Iconify.with(new MaterialModule());

        setInstance(this);
    }

    @Override
    protected AppComponent createComponent() {
        final MockAppComponent appComponent = DaggerMockAppComponent.builder()
                .appModule(new AppModule(mApplication))
                .providerConfigModule(new MockProviderConfigModule())
                .build();
        appComponent.inject(this);
        return appComponent;
    }
}
