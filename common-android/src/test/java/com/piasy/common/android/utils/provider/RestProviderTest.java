package com.piasy.common.android.utils.provider;

import junit.framework.Assert;

import org.junit.Test;

import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class RestProviderTest {

    private RestAdapter one, two;

    @Test
    public void testProvideEventBus() {
        one = RestProvider.provideRestAdapter();
        two = RestProvider.provideRestAdapter();

        Assert.assertTrue(one == two);
    }

    @Test
    public void testProvideEventBusConcurrently() {
        Thread t1 = new Thread(() -> {
            one = RestProvider.provideRestAdapter();
        });

        Thread t2 = new Thread(() -> {
            two = RestProvider.provideRestAdapter();
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
