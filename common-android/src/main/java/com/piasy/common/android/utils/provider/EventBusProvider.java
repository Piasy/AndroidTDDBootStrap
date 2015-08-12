package com.piasy.common.android.utils.provider;

import de.greenrobot.event.EventBus;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public class EventBusProvider {

    private EventBusProvider() {
    }

    public static EventBus provideEventBus() {
        return EventBusHolder.sEventBus;
    }

    private static class EventBusHolder {
        // lazy instantiate
        private static volatile EventBus sEventBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .eventInheritance(true)
                .throwSubscriberException(true)
                .build();
    }

}
