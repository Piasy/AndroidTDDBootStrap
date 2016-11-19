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

package com.github.piasy.bootstrap.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * DI module abstraction for Application scope.
 */
@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(final Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    Resources provideResources() {
        return mApplication.getResources();
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mApplication;
    }
}
