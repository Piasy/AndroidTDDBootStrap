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

package com.github.piasy.bootstrap.base.model.provider;

import com.github.piasy.bootstrap.model.AutoValueGsonAdapterFactory;
import com.google.gson.Gson;
import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/27.
 *
 * Expose gson provider for test.
 */
public final class GsonProviderExposure {

    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
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
    private static final GsonConfig CONFIG = GsonConfig.builder()
            .dateTimeFormatter(DATE_TIME_FORMATTER)
            .dateFormatString(TIME_FORMAT_ISO_8601)
            .autoGsonTypeAdapterFactory(AutoValueGsonAdapterFactory.create())
            .build();

    private GsonProviderExposure() {
        // no instance
    }

    /**
     * Expose gson provider for test.
     *
     * @return exposed gson.
     */
    public static Gson exposeGson() {
        return new ProviderModule().provideGson(CONFIG);
    }
}
