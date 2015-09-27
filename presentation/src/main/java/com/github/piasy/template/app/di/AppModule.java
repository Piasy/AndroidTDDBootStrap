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

package com.github.piasy.template.app.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.github.piasy.template.app.TemplateApp;
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

    private final TemplateApp mApplication;

    /**
     * Create module with {@link TemplateApp} object.
     *
     * @param application {@link TemplateApp} object.
     */
    public AppModule(final TemplateApp application) {
        mApplication = application;
    }

    /**
     * Provide {@link TemplateApp} object.
     *
     * @return {@link TemplateApp} object.
     */
    @Singleton
    @Provides
    TemplateApp provideTemplateApp() {
        return mApplication;
    }

    /**
     * Provide {@link Application} object.
     *
     * @return {@link Application} object.
     */
    @Singleton
    @Provides
    Application provideApplication() {
        return mApplication;
    }

    /**
     * Provide {@link Resources} object.
     *
     * @return {@link Resources} object.
     */
    @Singleton
    @Provides
    Resources provideResources() {
        return mApplication.getResources();
    }

    /**
     * Provide {@link Context} object.
     *
     * @return {@link Context} object.
     */
    @Singleton
    @Provides
    Context provideContext() {
        return mApplication;
    }
}
