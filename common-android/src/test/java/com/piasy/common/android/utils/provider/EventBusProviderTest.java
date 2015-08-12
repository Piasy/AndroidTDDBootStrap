package com.piasy.common.android.utils.provider;

import junit.framework.Assert;

import org.junit.Test;

import de.greenrobot.event.EventBus;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class EventBusProviderTest {

    private EventBus one, two;

    @Test
    public void testProvideEventBus() {
        one = EventBusProvider.provideEventBus();
        two = EventBusProvider.provideEventBus();

        Assert.assertTrue(one == two);
    }

    @Test
    public void testProvideEventBusConcurrently() {
        Thread t1 = new Thread(() -> {
            one = EventBusProvider.provideEventBus();
        });

        Thread t2 = new Thread(() -> {
            two = EventBusProvider.provideEventBus();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

            Assert.assertTrue(one == two);
        } catch (InterruptedException e) {
            Assert.assertTrue(false);
        }
    }

}
