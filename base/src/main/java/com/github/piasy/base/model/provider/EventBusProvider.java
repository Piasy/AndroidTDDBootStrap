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
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * A singleton provider providing {@link EventBus}.
 */
@SuppressWarnings("PMD.NonThreadSafeSingleton")
public final class EventBusProvider {

    private static volatile EventBus sEventBus;

    private EventBusProvider() {
        // singleton
    }

    /**
     * Provide the {@link EventBus} singleton instance.
     *
     * @return the singleton {@link EventBus}.
     */
    static EventBus provideEventBus(final Config config) {
        if (sEventBus == null) {
            synchronized (EventBusProvider.class) {
                if (sEventBus == null) {
                    sEventBus = EventBus.builder()
                            .logNoSubscriberMessages(config.logNoSubscriberMessages())
                            .sendNoSubscriberEvent(config.sendNoSubscriberEvent())
                            .eventInheritance(config.eventInheritance())
                            .throwSubscriberException(config.throwSubscriberException())
                            .build();
                }
            }
        }
        return sEventBus;
    }

    // CHECKSTYLE:OFF
    @AutoParcel
    public abstract static class Config {
        @NonNull
        public static Builder builder() {
            return new AutoParcel_EventBusProvider_Config.Builder();
        }

        public abstract boolean logNoSubscriberMessages();

        public abstract boolean sendNoSubscriberEvent();

        public abstract boolean eventInheritance();

        public abstract boolean throwSubscriberException();

        @AutoParcel.Builder
        public abstract static class Builder {
            public abstract Builder logNoSubscriberMessages(final boolean logNoSubscriberMessages);

            public abstract Builder sendNoSubscriberEvent(final boolean sendNoSubscriberEvent);

            public abstract Builder eventInheritance(final boolean eventInheritance);

            public abstract Builder throwSubscriberException(
                    final boolean throwSubscriberException);

            public abstract Config build();
        }
    }
    // CHECKSTYLE:ON
}
