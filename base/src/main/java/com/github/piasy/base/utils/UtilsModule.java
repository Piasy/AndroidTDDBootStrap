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

package com.github.piasy.base.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 *
 * Dagger2 {@link Module} for java utility dependencies.
 */
@Module
public class UtilsModule {

    private final Context mContext;

    /**
     * Create instance with the {@link Context} object.
     *
     * @param context the {@link Context} object.
     */
    public UtilsModule(@NonNull final Context context) {
        mContext = context;
    }

    /**
     * Provide {@link ScreenUtil} with the given context.
     *
     * @return the provided {@link ScreenUtil}
     */
    @Singleton
    @Provides
    ScreenUtil provideScreenUtil() {
        return new ScreenUtil(mContext);
    }

    /**
     * Provide {@link ToastUtil} with the given context.
     *
     * @return the provided {@link ToastUtil}
     */
    @Singleton
    @Provides
    ToastUtil provideToastUtil() {
        return new ToastUtilImpl(mContext);
    }
}
