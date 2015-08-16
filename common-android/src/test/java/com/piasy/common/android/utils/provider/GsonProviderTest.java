package com.piasy.common.android.utils.provider;

import com.google.gson.Gson;
import com.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GsonProviderTest extends BaseThreeTenBPTest {

    private Gson one, two;
    private ThreeTenABPDelegate mDelegate;

    @Before
    public void setUp() {
        initThreeTenABP();
        mDelegate = mock(ThreeTenABPDelegate.class);
    }

    @Test
    public void testProvideGson() {
        one = GsonProvider.provideGson(mDelegate);
        two = GsonProvider.provideGson(mDelegate);

        Assert.assertTrue(one == two);
    }

    @Test
    public void testProvideGsonConcurrently() {
        Thread t1 = new Thread(() -> {
            one = GsonProvider.provideGson(mDelegate);
        });

        Thread t2 = new Thread(() -> {
            two = GsonProvider.provideGson(mDelegate);
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
