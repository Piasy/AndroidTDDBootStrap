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

import com.github.piasy.common.android.jsr310.ThreeTenABPDelegate;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;
import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/6.
 *
 * Singleton provider module.
 */
@Module
public class ProviderModule {

    /**
     * Provide the {@link EventBus} singleton.
     *
     * @return the {@link EventBus} singleton.
     */
    @Singleton
    @Provides
    EventBus provideBus() {
        return EventBusProvider.provideEventBus();
    }

    /**
     * Provide the {@link Gson} singleton.
     *
     * @param delegate to initialize the JSR-310 library.
     * @return the {@link Gson} singleton.
     */
    @Singleton
    @Provides
    Gson provideGson(final ThreeTenABPDelegate delegate) {
        delegate.init();
        return GsonProvider.provideGson();
    }

    /**
     * Provide the {@link RestAdapter} singleton.
     *
     * @param delegate to initialize the JSR-310 library.
     * @return the {@link RestAdapter} singleton.
     */
    @Singleton
    @Provides
    RestAdapter provideRestAdapter(final ThreeTenABPDelegate delegate) {
        delegate.init();
        return RestProvider.provideRestAdapter();
    }
}
