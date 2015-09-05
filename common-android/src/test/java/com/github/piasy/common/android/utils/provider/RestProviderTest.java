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

        Assert.assertTrue(one.equals(two));
    }

    @Test
    public void testProvideRestAdapterConcurrently() {
        final Thread t1 = new Thread(() -> {
            one = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));
        });

        final Thread t2 = new Thread(() -> {
            two = RestProvider.provideRestAdapter(mock(ThreeTenABPDelegate.class));
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

            Assert.assertTrue(one.equals(two));
        } catch (InterruptedException e) {
            Assert.assertTrue(false);
        }
    }
}
