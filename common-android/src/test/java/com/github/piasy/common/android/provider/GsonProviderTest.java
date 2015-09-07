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

import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GsonProviderTest extends BaseThreeTenBPTest {

    private Gson one, two;

    @Before
    public void setUp() {
        initThreeTenABP();
    }

    @Test
    public void testProvideGson() {
        one = GsonProvider.provideGson();
        two = GsonProvider.provideGson();

        Assert.assertTrue(one.equals(two));
    }

    @Test
    public void testProvideGsonConcurrently() {
        final Thread t1 = new Thread(() -> {
            one = GsonProvider.provideGson();
        });

        final Thread t2 = new Thread(() -> {
            two = GsonProvider.provideGson();
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
