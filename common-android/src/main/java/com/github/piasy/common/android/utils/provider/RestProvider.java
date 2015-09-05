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

package com.github.piasy.common.android.utils.provider;

import com.github.piasy.common.Constants;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * A singleton provider providing {@link retrofit.RestAdapter}.
 */
public final class RestProvider {

    // need delegate parameter, backing to the DCL singleton implementation.
    /*public static RestAdapter provideRestAdapter() {
        return RestAdapterHolder.sRestAdapter;
    }

    private static class RestAdapterHolder {
        // lazy instantiate
        private static volatile RestAdapter sRestAdapter =
                new RestAdapter.Builder().setEndpoint(Constants.GITHUB_SERVER_ENDPOINT)
                        .setConverter(new GsonConverter(GsonProvider.provideGson(
                                // not available here
                                // back to dcl singleton implementation :(
                        )))
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();
    }*/

    private static volatile RestAdapter sRestAdapter;

    private RestProvider() {
        // singleton
    }

    /**
     * Provide the {@link retrofit.RestAdapter} singleton instance. The {@link ThreeTenABPDelegate}
     * parameter is used for the {@link GsonProvider} to provide {@link com.google.gson.Gson}
     * instance.
     *
     * @param delegate the {@link ThreeTenABPDelegate} instance used for {@link GsonProvider} to
     * provide {@link com.google.gson.Gson} instance.
     * @return the singleton {@link RestAdapter}.
     */
    @SuppressWarnings("PMD.NonThreadSafeSingleton")
    public static RestAdapter provideRestAdapter(final ThreeTenABPDelegate delegate) {
        if (sRestAdapter == null) {
            synchronized (RestProvider.class) {
                if (sRestAdapter == null) {
                    sRestAdapter =
                            new RestAdapter.Builder().setEndpoint(Constants.GITHUB_SERVER_ENDPOINT)
                                    .setConverter(
                                            new GsonConverter(GsonProvider.provideGson(delegate)))
                                    .setLogLevel(RestAdapter.LogLevel.FULL)
                                    .build();
                }
            }
        }

        return sRestAdapter;
    }
}
