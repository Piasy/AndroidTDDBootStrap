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

package com.github.piasy.lg.di;

import com.github.piasy.lg.BuildConfig;
import com.github.piasy.base.model.jsr310.ThreeTenABPDelegate;
import com.github.piasy.base.model.provider.EventBusProvider;
import com.github.piasy.base.model.provider.GsonProvider;
import com.github.piasy.base.model.provider.HttpClientProvider;
import com.github.piasy.base.model.provider.RetrofitProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by piasy on 15/8/10.
 *
 * DB Module class for dagger2.
 */
@Module
public class ProviderConfigModule {

    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Singleton
    @Provides
    HttpClientProvider.Config provideHttpClientConfig() {
        return HttpClientProvider.Config.builder()
                .enableLog("debug".equals(BuildConfig.BUILD_TYPE))
                .build();
    }

    @Singleton
    @Provides
    RetrofitProvider.Config provideRestConfig() {
        return RetrofitProvider.Config.builder().baseUrl(BuildConfig.API_BASE_URL).build();
    }

    @Singleton
    @Provides
    EventBusProvider.Config provideEventBusConfig() {
        return EventBusProvider.Config.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .eventInheritance(true)
                .throwSubscriberException(true)
                .build();
    }

    @Singleton
    @Provides
    GsonProvider.Config provideGsonConfig(final ThreeTenABPDelegate delegate) {
        delegate.init();
        final DateTimeFormatter dateTimeFormatter =
                new DateTimeFormatterBuilder().parseCaseInsensitive()
                        .append(DateTimeFormatter.ISO_LOCAL_DATE)
                        .appendLiteral('T')
                        .appendValue(ChronoField.HOUR_OF_DAY, 2)
                        .appendLiteral(':')
                        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                        .optionalStart()
                        .appendLiteral(':')
                        .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                        .appendLiteral('Z')
                        .toFormatter()
                        .withResolverStyle(ResolverStyle.STRICT)
                        .withChronology(IsoChronology.INSTANCE)
                        .withZone(ZoneId.systemDefault());

        return GsonProvider.Config.builder()
                .dateFormatString(TIME_FORMAT_ISO_8601)
                .dateTimeFormatter(dateTimeFormatter)
                .build();
    }
}
