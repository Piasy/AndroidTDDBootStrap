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

package com.github.piasy.octostars.di;

import android.content.Context;
import com.github.piasy.bootstrap.base.model.jsr310.ThreeTenABPDelegate;
import com.github.piasy.bootstrap.base.model.provider.BriteDbConfig;
import com.github.piasy.bootstrap.base.model.provider.EventBusConfig;
import com.github.piasy.bootstrap.base.model.provider.GsonConfig;
import com.github.piasy.bootstrap.base.model.provider.HttpClientConfig;
import com.github.piasy.bootstrap.base.model.provider.RetrofitConfig;
import com.github.piasy.bootstrap.base.model.provider.SharedPreferenceConfig;
import com.github.piasy.octostars.business.BuildConfig;
import com.github.piasy.octostars.bridge.DbOpenHelper;
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

    private static final boolean DEBUG = !"release".equals(BuildConfig.BUILD_TYPE);
    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Singleton
    @Provides
    DateTimeFormatter provideDateTimeFormatter(final ThreeTenABPDelegate delegate) {
        delegate.init();
        return new DateTimeFormatterBuilder().parseCaseInsensitive()
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
    }

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

    @Singleton
    @Provides
    BriteDbConfig provideBriteDbConfig(final DbOpenHelper dbOpenHelper) {
        return BriteDbConfig.builder()
                .sqliteOpenHelper(dbOpenHelper)
                .enableLogging(DEBUG)
                .build();
    }

    @Singleton
    @Provides
    GsonConfig provideGsonConfig(final DateTimeFormatter formatter) {
        return GsonConfig.builder()
                .dateFormatString(TIME_FORMAT_ISO_8601)
                .dateTimeFormatter(formatter)
                .build();
    }

    @Singleton
    @Provides
    SharedPreferenceConfig provideSharedPreferenceConfig() {
        return SharedPreferenceConfig.builder()
                .name("OctoStars_sp")
                .mode(Context.MODE_PRIVATE)
                .build();
    }
}
