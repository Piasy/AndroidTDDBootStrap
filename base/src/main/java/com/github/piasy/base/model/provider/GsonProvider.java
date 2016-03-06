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

package com.github.piasy.base.model.provider;

import android.support.annotation.NonNull;
import auto.parcel.AutoParcel;
import com.github.piasy.base.model.gson.AutoGenTypeAdapterFactory;
import com.github.piasy.base.model.jsr310.CustomZonedDateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * A singleton provider providing {@link Gson}.
 */
@SuppressWarnings("PMD.NonThreadSafeSingleton")
public final class GsonProvider {

    private static volatile Gson sGson;

    private GsonProvider() {
        // singleton
    }

    /**
     * Provide the {@link Gson} singleton instance. Should be only called in test cases, besides
     * {@link ProviderModule}.
     *
     * @return the singleton {@link Gson}.
     */
    static Gson provideGson(final Config config) {
        if (sGson == null) {
            synchronized (GsonProvider.class) {
                if (sGson == null) {
                    sGson = new GsonBuilder()
                            //not exclude for auto parcel
                            //.excludeFieldsWithoutExposeAnnotation()
                            .registerTypeAdapterFactory(new AutoGenTypeAdapterFactory())
                            .registerTypeAdapter(ZonedDateTime.class,
                                    new CustomZonedDateTimeConverter(config.dateTimeFormatter()))
                            .setDateFormat(config.dateFormatString())
                            .setPrettyPrinting()
                            .create();
                }
            }
        }
        return sGson;
    }

    // CHECKSTYLE:OFF
    @AutoParcel
    public abstract static class Config {
        @NonNull
        public static Builder builder() {
            return new AutoParcel_GsonProvider_Config.Builder();
        }

        public abstract DateTimeFormatter dateTimeFormatter();

        public abstract String dateFormatString();

        @AutoParcel.Builder
        public abstract static class Builder {
            public abstract Builder dateTimeFormatter(final DateTimeFormatter dateTimeFormatter);

            public abstract Builder dateFormatString(final String dateFormatString);

            public abstract Config build();
        }
    }
    // CHECKSTYLE:ON
}
