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

import android.support.annotation.NonNull;
import com.github.piasy.bootstrap.BuildConfig;
import com.github.piasy.bootstrap.base.model.provider.BriteDbConfig;
import com.github.piasy.bootstrap.base.model.provider.EventBusConfig;
import com.github.piasy.bootstrap.base.model.provider.HttpClientConfig;
import com.github.piasy.bootstrap.base.model.provider.RetrofitConfig;
import com.github.piasy.bootstrap.model.DbOpenHelper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by piasy on 15/8/10.
 *
 * DB Module class for dagger2.
 */
@Module
public class ProviderConfigModule {

    private static final boolean DEBUG = "debug".equals(BuildConfig.BUILD_TYPE);

    @Singleton
    @Provides
    HttpClientConfig provideHttpClientConfig() {
        return HttpClientConfig.builder().enableLog(DEBUG).build();
    }

    @Singleton
    @Provides
    RetrofitConfig provideRestConfig() {
        return RetrofitConfig.builder().baseUrl(BuildConfig.API_BASE_URL).build();
    }

    @Singleton
    @Provides
    EventBusConfig provideEventBusConfig() {
        return EventBusConfig.builder()
                .logNoSubscriberMessages(DEBUG)
                .sendNoSubscriberEvent(DEBUG)
                .eventInheritance(true)
                .throwSubscriberException(true)
                .build();
    }

    @Provides
    @Singleton
    BriteDbConfig provideBriteDbConfig(@NonNull final DbOpenHelper dbOpenHelper) {
        return BriteDbConfig.builder()
                .sqliteOpenHelper(dbOpenHelper)
                .enableLogging(DEBUG)
                .build();
    }
}
