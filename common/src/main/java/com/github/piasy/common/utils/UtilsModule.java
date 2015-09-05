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

package com.github.piasy.common.utils;

import com.github.piasy.common.Constants;
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

    /**
     * Provide {@link EmailUtil} instance.
     *
     * @return the provided {@link EmailUtil} instance.
     */
    @Singleton
    @Provides
    EmailUtil provideEmailUtil() {
        return new EmailUtil(Constants.RE_PATTERTNS_EMAIL_PATTERN);
    }
}
