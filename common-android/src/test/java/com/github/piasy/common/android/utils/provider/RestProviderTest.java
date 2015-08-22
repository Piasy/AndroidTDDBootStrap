package com.github.piasy.common.android.utils.provider;

import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import junit.framework.Assert;
import org.junit.Test;
import retrofit.RestAdapter;

import static org.mockito.Mockito.mock;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */

public class RestProviderTest {

    private RestAdapter one, two;

    @Test
    public void testProvideRestAdapter() {
        one = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));
        two = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));

        Assert.assertTrue(one == two);
    }

    @Test
    public void testProvideRestAdapterConcurrently() {
        Thread t1 = new Thread(() -> {
            one = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));
        });

        Thread t2 = new Thread(() -> {
            two = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));
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
