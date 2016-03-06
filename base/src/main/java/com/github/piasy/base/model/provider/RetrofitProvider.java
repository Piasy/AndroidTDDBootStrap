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
import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * A singleton provider providing {@link Retrofit}.
 */
@SuppressWarnings("PMD.NonThreadSafeSingleton")
public final class RetrofitProvider {
    private static volatile Retrofit sRetrofit;

    private RetrofitProvider() {
        // singleton
    }

    /**
     * Provide the {@link Retrofit} singleton instance.
     *
     * @return the singleton {@link Retrofit}.
     */
    static Retrofit provideRetrofit(final Config config, final Gson gson) {
        if (sRetrofit == null) {
            synchronized (RetrofitProvider.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder().baseUrl(config.baseUrl())
                            .client(HttpClientProvider.provideHttpClient())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    // CHECKSTYLE:OFF
    @AutoParcel
    public abstract static class Config {
        @NonNull
        public static Builder builder() {
            return new AutoParcel_RetrofitProvider_Config.Builder();
        }

        public abstract String baseUrl();

        @AutoParcel.Builder
        public abstract static class Builder {
            public abstract Builder baseUrl(final String baseUrl);

            public abstract Config build();
        }
    }
    // CHECKSTYLE:ON
}
