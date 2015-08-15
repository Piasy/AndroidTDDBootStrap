package com.piasy.common.android.utils.provider;

import com.google.gson.Gson;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GsonProviderTest {

    private Gson one, two;

    @Test
    public void testProvideEventBus() {
        one = GsonProvider.provideGson();
        two = GsonProvider.provideGson();

        Assert.assertTrue(one == two);
    }

    @Test
    public void testProvideEventBusConcurrently() {
        Thread t1 = new Thread(() -> {
            one = GsonProvider.provideGson();
        });

        Thread t2 = new Thread(() -> {
            two = GsonProvider.provideGson();
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
