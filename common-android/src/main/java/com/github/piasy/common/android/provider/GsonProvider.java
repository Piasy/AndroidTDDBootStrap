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

package com.github.piasy.common.android.provider;

import com.github.piasy.common.Constants;
import com.github.piasy.common.android.utils.model.CustomZonedDateTimeConverter;
import com.github.piasy.common.model.AutoGenTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * A singleton provider providing {@link Gson}.
 */
final class GsonProvider {

    private GsonProvider() {
        // singleton
    }

    /**
     * Provide the {@link Gson} singleton instance. Should be only called in test cases, besides
     * {@link ProviderModule}.
     *
     * @return the singleton {@link Gson}.
     */
    static Gson provideGson() {
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        // lazy instantiate
        private static volatile DateTimeFormatter sDateTimeFormatter =
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

        private static volatile Gson sGson = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()   //not exclude for auto parcel
                .registerTypeAdapterFactory(new AutoGenTypeAdapterFactory())
                .registerTypeAdapter(ZonedDateTime.class,
                        new CustomZonedDateTimeConverter(sDateTimeFormatter))
                .setDateFormat(Constants.TIME_FORMAT_ISO_8601)
                .setPrettyPrinting()
                .create();
    }
}
